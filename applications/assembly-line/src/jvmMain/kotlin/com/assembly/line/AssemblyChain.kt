package com.assembly.line

import com.assembly.console.colour.COLOUR
import com.assembly.console.colour.toColour
import com.assembly.entity.AssemblyCarEntity
import com.assembly.entity.Car
import com.assembly.log.getLogger
import kotlinx.coroutines.sync.withLock

/**
 * A DSL to allow chaining AssemblyStations so that the first station always
 * receives an AssemblyCarEntity (I) and the last station always
 * produces a Car (O)
 *
 * Assembly Chain doesn't currently support I -> J -> K -> O,
 * ony I -> I -> I -> O -> O
 *
 */
class AssemblyChain<I : AssemblyCarEntity, O : Car>(private val name: String) {

	private var maxPosition: Int = 0

	companion object {
		val log = getLogger()
	}

	private val internalStations = mutableListOf<AssemblyStation<*, *>>()

	// get a list of stations, but with negative index stations filtered out and added to the end
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

	// allow chain + station which would return a station
	// since station too overrides +, you can also do station + station
	operator fun <T> plus(station: AssemblyStation<I, T>): AssemblyStation<I, T> {
		return add(station)
	}

	// add another station to the chain
	// this add is not meant to be run in parallel, but for safety, it's synchronised
	fun <I, T : Any?> add(station: AssemblyStation<I, T>): AssemblyStation<I, T> {
		return synchronized(this) {

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
			newStation
		}
	}

	// lookup the next station given the current station
	fun getNextStation(currentStation: AssemblyStation<*, *>?): AssemblyStation<*, *>? {
		val allStations = stations
		// if no station given, return this first option in the list
		if (currentStation == null) {
			return allStations.first()
		}
		// find the index of the current station in the list of stations
		val indexOfCurrentStation = allStations.indexOfFirst { it.position == currentStation.position }
		// if no index found, return the first station
		if (indexOfCurrentStation < 0) {
			return allStations.first()
		}
		// get the first station after the indexOfCurrentStation
		return allStations.filterIndexed { index, _ ->
			index > indexOfCurrentStation
		}.firstOrNull()
	}

	// TODO: investigate if there is a cleaner way to avoid casting
	//     not proud of casting these Station types, but it gets the job done for now
	@Suppress("UNCHECKED_CAST")
	suspend fun process(input: I, printStation: Boolean = false): O {
		var result: Any? = input
		var currentStation = stations.first()
		while (true) {
			// we assume that only one operation can happen at a time at each station concurrently
			result = currentStation.mutex.withLock {
				if (printStation) {
					log.info(" $currentStation ".toColour(COLOUR.ANSI_BLACK_BACKGROUND, COLOUR.ANSI_WHITE))
				}
				(currentStation.handler as (suspend Any?.() -> Any?)).invoke(result)
			}
			currentStation = if (result == null) {
				break
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

	override fun toString(): String {
		return "${this.name}: ${this.stations.joinToString(", ") { it.toString() }}"
	}

}
