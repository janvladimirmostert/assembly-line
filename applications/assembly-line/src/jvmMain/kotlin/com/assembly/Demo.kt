package com.assembly

import com.assembly.brand.ford.ModelTAssembly
import com.assembly.brand.ford.ModelTAssemblyLine
import com.assembly.brand.tesla.CyberTruckAssembly
import com.assembly.brand.tesla.CyberTruckAssemblyLine
import com.assembly.console.colour.COLOUR.*
import com.assembly.console.colour.toColour
import com.assembly.log.getLogger
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

object Demo {

	private val log = getLogger()
	private val teslaCyberTruckAssemblyLine = CyberTruckAssemblyLine()
	private val fordModelTAssemblyLine = ModelTAssemblyLine()

	@JvmStatic
	fun main(args: Array<String>) = runBlocking {

		val set = mutableSetOf<Int>()
		repeat(1000) {
			set.add(Random.nextInt(100))
		}
		println(set.sorted())


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

	}

}


