package com.assembly

import com.assembly.console.colour.toColour
import com.assembly.brand.tesla.CyberTruckAssembly
import com.assembly.brand.tesla.CyberTruckAssemblyLine
import com.assembly.console.colour.COLOUR.*
import com.assembly.log.getLogger
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

object Demo {

	private val log = getLogger()
	private val cyberTruckAssemblyLine = CyberTruckAssemblyLine()

	@JvmStatic
	fun main(args: Array<String>) = runBlocking {

		log.info(javaClass.getResourceAsStream("/art.txt")?.use {
			String(it.readAllBytes()).toColour(ANSI_GREEN)
		})

		val car = cyberTruckAssemblyLine.produce(
			CyberTruckAssembly(
				trackingColour = ANSI_BLUE,
				expectedBatteries = 100,
				expectedTintedWindows = true,
				expectedBulletProofWindows = true,
			)
		)
		log.info("\n$car".toColour(car.trackingColour))




		Unit
	}

}


