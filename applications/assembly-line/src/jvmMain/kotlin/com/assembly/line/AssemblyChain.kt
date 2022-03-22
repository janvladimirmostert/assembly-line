package com.assembly.line

import com.assembly.entity.AssemblyCarEntity
import com.assembly.entity.Car
import com.assembly.log.getLogger
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class AssemblyChain<I : AssemblyCarEntity, O : Car>(private val name: String) {

	private val mutex = Mutex()
	private var maxPosition: Int = 0

	companion object {
		val log = getLogger()
	}

	private val internalStations = mutableListOf<AssemblyStation<*, *>>()

	val stations: List<AssemblyStation<*, *>>
		get() {
			val positiveIndexedStations = internalStations.filter {
				(it.position ?: 0) >= 0
			}.sortedBy {
				it.position
			}.toTypedArray()
			val negativeIndexStations = internalStations.filter {
				(it.position ?: 0) < 0
			}.sortedBy {
				it.position
			}.reversed().toTypedArray()
			return listOf(*positiveIndexedStations, *negativeIndexStations)
		}

	operator fun <T> plus(station: AssemblyStation<I, T>): AssemblyStation<I, T> {
		return add(station)
	}

	override fun toString(): String {
		return "${this.name}: ${this.stations.joinToString(", ") { it.toString() }}"
	}

	fun <I, T : Any?> add(station: AssemblyStation<I, T>): AssemblyStation<I, T> {
		val newStation = AssemblyStation<I, T>(
			name = station.name,
			position = station.position.let { stationPosition ->
				if (stationPosition != null) {
					if (stationPosition > maxPosition) {
						maxPosition = stationPosition + 1
					}
					stationPosition
				} else {
					++maxPosition
				}
			},
			handler = station.handler,
			partOf = this
		)
		internalStations.add(newStation)
		return newStation
	}

	fun getNextStation(currentStation: AssemblyStation<*, *>?): AssemblyStation<*, *>? {
		val allStations = stations
		if (currentStation == null) {
			return allStations.first()
		}
		val indexOfCurrentStation = allStations.indexOfFirst { it.position == currentStation.position }
		if (indexOfCurrentStation < 0) {
			return allStations.first()
		}
		return allStations.filterIndexed { index, _ ->
			index > indexOfCurrentStation
		}.firstOrNull()
	}

	// TODO: investigate if there is a cleaner way to avoid casting
	//     not proud of casting these Station types, but it gets the job done for now
	@Suppress("UNCHECKED_CAST")
	suspend fun process(input: I): O {
		var result: Any? = input
		var currentStation = stations.first()
		while (true) {
			// we assume that only one operation can happen at a time at each station concurrently
			result = mutex.withLock {
				(currentStation.handler as (suspend Any?.() -> Any?)).invoke(result)
			}
			currentStation = if (result == null) {
				break;
			} else if (result is AssemblyRedirect<*, *>) {
				val redirect = result
				result = redirect.data
				redirect.station
			} else {
				getNextStation(currentStation) ?: break
			}
		}


		return result as O
	}

}
