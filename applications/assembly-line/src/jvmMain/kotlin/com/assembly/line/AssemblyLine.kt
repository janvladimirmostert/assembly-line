package com.assembly.line

import kotlinx.coroutines.sync.Mutex

class AssemblyLine<T>(private val name: String) {

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

	operator fun <O> plus(station: AssemblyStation<T, O>): AssemblyStation<T, O> {
		return add(station)
	}

	override fun toString(): String {
		return "${this.name}: ${this.stations.joinToString(", ") { it.toString() }}"
	}

	fun <O, R> add(station: AssemblyStation<O, R>): AssemblyStation<O, R> {
		val newStation = AssemblyStation(
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

}
