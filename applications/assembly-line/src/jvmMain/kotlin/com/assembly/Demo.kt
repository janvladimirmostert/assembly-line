package com.assembly

import com.assembly.brand.ford.ModelTAssembly
import com.assembly.brand.ford.ModelTAssemblyLine
import com.assembly.brand.ford.ModelTCar
import com.assembly.brand.tesla.CyberTruckAssembly
import com.assembly.brand.tesla.CyberTruckAssemblyLine
import com.assembly.console.colour.COLOUR.*
import com.assembly.console.colour.toColour
import com.assembly.entity.AssemblyLine
import com.assembly.line.AssemblyChain
import com.assembly.line.AssemblyStation
import com.assembly.log.getLogger
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object Demo {

	private val log = getLogger()
	private val teslaCyberTruckAssemblyLine = CyberTruckAssemblyLine()
	private val fordModelTAssemblyLine = ModelTAssemblyLine()

	@JvmStatic
	fun main(args: Array<String>) = runBlocking {

		// ascii art is important
		log.info(javaClass.getResourceAsStream("/art.txt")?.use {
			String(it.readAllBytes()).toColour(ANSI_GREEN)
		})

		// let's produce a Tesla Cybertruck via the CyberTruckAssemblyLine
		val car1 = teslaCyberTruckAssemblyLine.produce(
			CyberTruckAssembly(
				trackingColour = ANSI_BLUE,
				expectedBatteries = 100,
				expectedTintedWindows = true,
				expectedBulletProofWindows = true,
			)
		)
		log.info("\n$car1".toColour(car1.trackingColour))

		// let's produce a second Tesla Cybertruck with fewer features via the CyberTruckAssemblyLine
		val car2 = teslaCyberTruckAssemblyLine.produce(
			CyberTruckAssembly(
				trackingColour = ANSI_GREEN,
				expectedBatteries = 50,
				expectedTintedWindows = false,
				expectedBulletProofWindows = false,
			)
		)
		log.info("\n$car2".toColour(car2.trackingColour))

		// let's produce a very old Ford Model T which has no configuration options
		val car3 = fordModelTAssemblyLine.produce(
			ModelTAssembly(
				trackingColour = ANSI_CYAN,
			)
		)
		log.info("\n$car3".toColour(car3.trackingColour))

		// let's adjust the production line to allow polishing Ford Model T
		val fordModelTAssemblyLineWithPolish = object : AssemblyLine<ModelTAssembly, ModelTCar> {
			private val newChain = AssemblyChain<ModelTAssembly, ModelTCar>("Polishable Model T")

			init {
				fordModelTAssemblyLine.expose { chain ->
					chain.stations.forEach { station ->
						newChain.add(AssemblyStation(
							partOf = newChain,
							name = station.name,
							handler = station.handler,
							position = station.position
						))
					}
				}
				newChain.add(AssemblyStation<ModelTCar, ModelTCar>(name = "Polish") {
					it.copy(
						polished = true
					)
				})
				println(newChain)
			}

			override suspend fun produce(assembly: ModelTAssembly): ModelTCar {
				log.info(newChain.toString().toColour(assembly.trackingColour))
				return newChain.process(assembly, true)
			}
		}

		// let's try and handle concurrent orders via a single production line
		listOf(ANSI_RED, ANSI_GREEN, ANSI_BLUE, ANSI_YELLOW, ANSI_BLUE, ANSI_BLACK).forEach { color ->
			launch {
				val car4 = fordModelTAssemblyLineWithPolish.produce(ModelTAssembly(
					trackingColour = color,
				))
				log.info(car4.toString().toColour(car4.trackingColour))
			}
		}

	}

}


