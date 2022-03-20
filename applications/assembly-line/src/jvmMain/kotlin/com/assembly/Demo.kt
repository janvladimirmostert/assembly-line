package com.assembly

import com.assembly.console.colour.COLOUR.ANSI_GREEN
import com.assembly.console.colour.toColour
import com.assembly.log.getLogger
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex


//class AssemblyCarEntityIml : AssemblyCarEntity {
//
//	companion object {
//		private val log = getLogger()
//	}
//
//	override suspend fun paint() {
//		log.info("painting!!!")
//	}
//
//	override suspend fun build(): Car {
//		log.info("building!!!")
//	}
//}


//class AssemblyLine(
//	vararg val activity: Activity,
//) {
//
//	companion object {
//		val log = getLogger()
//	}
//
//	suspend fun produce(e: AssemblyCarEntity) {
//		this.activity.filter { it !is QualityCheck }.forEach { activity ->
//			log.info(activity::class)
//			activity.execute()
//		}
//		this.activity.filterIsInstance<QualityCheck>().forEach { activity ->
//			log.info(activity as QualityCheck)
//			activity.execute()
//		}
//	}
//}
//


class AssemblyLine<T>(
	private val initialValue: T,
) {

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

	suspend operator fun <O> plus(station: Station<T, O>): Station<T, O> {
		return add(station)
	}

	override fun toString(): String {
		return this.stations.joinToString(", ") { it.toString() }
	}

	fun <O, R> add(station: Station<O, R>): Station<O, R> {
		val newStation = Station(
			name = station.name,
			position = station.position.let { stationPosition ->
				val pos = stationPosition ?: 0
				if (pos > maxPosition) {
					maxPosition = pos
				}
				++maxPosition
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

	suspend operator fun <R> plus(station: Station<O, R>): Station<O, R> {
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

		val line = AssemblyLine("")
		val paint = line + Station(name = "paint", position = 0) {
			0
		}
		val build = paint + Station(name = "build") {
			0.5
		}

		val qa = build + Station(name = "qa", position = -1) {
			0
		}

		val blah = qa + Station(name = "blah") {
			""
		}

		println(blah)
		println(line)




	}

}


