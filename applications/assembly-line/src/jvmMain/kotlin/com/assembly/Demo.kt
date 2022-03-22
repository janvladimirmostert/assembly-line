package com.assembly

import com.assembly.brand.ford.ModelTAssembly
import com.assembly.brand.ford.ModelTAssemblyLine
import com.assembly.brand.ford.ModelTCar
import com.assembly.brand.ford.ModelTCarPolished
import com.assembly.brand.tesla.CyberTruckAssembly
import com.assembly.brand.tesla.CyberTruckAssemblyLine
import com.assembly.console.colour.COLOUR.*
import com.assembly.console.colour.toColour
import com.assembly.entity.AssemblyLine
import com.assembly.line.AssemblyChain
import com.assembly.line.AssemblyStation
import com.assembly.log.getLogger
import kotlinx.coroutines.runBlocking

object Demo {

	private val log = getLogger()
	private val teslaCyberTruckAssemblyLine = CyberTruckAssemblyLine()
	private val fordModelTAssemblyLine = ModelTAssemblyLine()

	@JvmStatic
	fun main(args: Array<String>) = runBlocking {

		log.info(javaClass.getResourceAsStream("/art.txt")?.use {
			String(it.readAllBytes()).toColour(ANSI_GREEN)
		})

		val car1 = teslaCyberTruckAssemblyLine.produce(
			CyberTruckAssembly(
				trackingColour = ANSI_BLUE,
				expectedBatteries = 100,
				expectedTintedWindows = true,
				expectedBulletProofWindows = true,
			)
		)
		log.info("\n$car1".toColour(car1.trackingColour))

		val car2 = teslaCyberTruckAssemblyLine.produce(
			CyberTruckAssembly(
				trackingColour = ANSI_GREEN,
				expectedBatteries = 50,
				expectedTintedWindows = false,
				expectedBulletProofWindows = false,
			)
		)
		log.info("\n$car2".toColour(car2.trackingColour))

		val car3 = fordModelTAssemblyLine.produce(
			ModelTAssembly(
				trackingColour = ANSI_CYAN,
			)
		)
		log.info("\n$car3".toColour(car3.trackingColour))

		val fordModelTAssemblyLineWithPolish = object : AssemblyLine<ModelTAssembly, ModelTCarPolished> {
			private val newChain = AssemblyChain<ModelTAssembly, ModelTCarPolished>("Polishable Model T")

			init {
				fordModelTAssemblyLine.expose { chain ->
					chain.stations.forEach { station ->
						newChain.add(station)
					}
				}
				newChain.add(AssemblyStation<ModelTCar, ModelTCarPolished>("Polish") {
					ModelTCarPolished(
						assembly = it.assembly,
						trackingColour = it.trackingColour,
						interiorAssembled = it.interiorAssembled,
						mechanicAssembled = it.mechanicAssembled,
						painted = it.painted,
						polished = true
					)
				})

			}

			override suspend fun produce(assembly: ModelTAssembly): ModelTCarPolished {
				log.info(newChain.toString().toColour(assembly.trackingColour))
				return newChain.process(assembly)
			}
		}
		fordModelTAssemblyLineWithPolish.produce(ModelTAssembly(
			trackingColour = ANSI_YELLOW,
		))




		Unit

	}

}


