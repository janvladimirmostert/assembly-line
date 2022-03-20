package com.assembly

import com.assembly.console.colour.COLOUR
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
class AssemblyLine<I>(
	private val initialValue: I,
) {

	private val mutex = Mutex()
	private var maxPosition: Int = 0
	private val stations = mutableListOf<Station<*, *>>()

	suspend operator fun <O> plus(station: Station<I, O>): AssemblyLine<O> {
		mutex.lock {
			if (station.position == null) {
				maxPosition++
				stations.add(
					Station(
						position = maxPosition,
						handler = station.handler
					)
				)
			} else {
				if (station.position > maxPosition) {
					maxPosition = station.position
				}
				stations.add(station)
			}
		}

		return AssemblyLine(station.handler(initialValue))
	}
}

class Station<I,O>(val position: Int? = null, val handler: I.() -> O) {

}


object Demo {

	private val log = getLogger()


	@JvmStatic
	fun main(args: Array<String>) = runBlocking {

		log.info(javaClass.getResourceAsStream("/art.txt")?.use {
			String(it.readAllBytes()).toColour(ANSI_GREEN)
		})

		val line = AssemblyLine("")
		val paint = line + Station(0) {
			0
		}
		val build = paint + Station(1) {

		}

		val qa = build + Station(-1) {
			0
		}

		println(qa)




//		line += Station {
//			123
//		}
//		line += Station {
//		 0.3
//		}
//
//		line += Station(-1) {
//
//		}
//
//		line.stations.forEach {
//			println(it.position)
//		}






//
//		val k = Paint<String, Int> {
//			0
//		}
//
//
//		line2 += Paint {
//			""
//		}
//
//
//		println(line2)
//
//
//
//

//			.blah {
//			0
//		}.blah {
//			3
//		}.value

//		println(line2)



//
//		AssemblyLine(
//			QualityCheck {
//				log.info("checking car quality ")
//			},
//			Paint {
//				delay(100)
//				log.info("painting")
//				delay(100)
//			},
//			Paint {
//				delay(100)
//				log.info("painting again")
//				delay(100)
//			},
//			AssemblyMechanich {
//				log.info("assembling mechanic")
//			},
//
//			Build {
//				delay(21)
//				log.info("building")
//				delay(44)
//			},
//			AssemblyInterior {
//				log.info("assembling interior")
//			},
//
//			).produce(AssemblyCarEntityIml())
//
//		TeslaCybertruckAssemblyLine().produce(AssemblyCarEntityIml())


	}

}


