package com.assembly

import com.assembly.console.colour.COLOUR.ANSI_GREEN
import com.assembly.console.colour.toColour
import com.assembly.entity.tesla.CyberTruckAssembly
import com.assembly.line.TeslaCybertruckAssemblyLine
import com.assembly.log.getLogger
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex

class AssemblyLine<T> {

	private val mutex = Mutex()
	private var maxPosition: Int = 0
	private val internalStations = mutableListOf<Station<*, *>>()
	val stations: List<Station<*, *>>
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

	operator fun <O> plus(station: Station<T, O>): Station<T, O> {
		return add(station)
	}

	override fun toString(): String {
		return this.stations.joinToString(", ") { it.toString() }
	}

	fun <O, R> add(station: Station<O, R>): Station<O, R> {
		val newStation = Station(
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

class Station<I, O>(
	val name: String,
	val position: Int? = null,
	val partOf: AssemblyLine<*>? = null,
	val handler: I.() -> O,
) {
	override fun toString(): String {
		return "$name:$position"
	}

	operator fun <R> plus(station: Station<O, R>): Station<O, R> {
		if (this.partOf == null) {
			TODO("handle this")
		}
		return this.partOf.add(station)
	}



}


object Demo {

	private val log = getLogger()


	@JvmStatic
	fun main(args: Array<String>) = runBlocking {

		log.info(javaClass.getResourceAsStream("/art.txt")?.use {
			String(it.readAllBytes()).toColour(ANSI_GREEN)
		})

		TeslaCybertruckAssemblyLine().produce(
			CyberTruckAssembly()
		)



//		val line = AssemblyLine("")
//		val paint = line + Station(name = "paint", position = 10) {
//			0
//		}
//		val build = paint + Station(name = "build") {
//			0.5
//		}
//
//		val qa = build + Station(name = "qa", position = -1) {
//			0
//		}
//
//		val blah = paint + Station(name = "blah", 10) {
//			""
//		}




		Unit
	}

}


