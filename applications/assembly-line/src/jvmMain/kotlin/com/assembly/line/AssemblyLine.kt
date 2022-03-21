package com.assembly.line

import com.assembly.entity.Assembly
import com.assembly.entity.Car
import kotlinx.coroutines.sync.Mutex

class AssemblyLine<I : Assembly, O : Car>(private val name: String) {

	private val mutex = Mutex()
	private var maxPosition: Int = 0

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

	fun <I, T: Any?> add(station: AssemblyStation<I, T>): AssemblyStation<I, T> {
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

	fun process(input: I): O {
		var result: Any? = input
		stations.forEach {
			result = (it.handler as (Any?.() -> Any?)).invoke(result)
			println(result)
		}
		return result as O
	}

}
