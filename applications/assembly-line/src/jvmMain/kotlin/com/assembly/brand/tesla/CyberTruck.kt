package com.assembly.brand.tesla

import com.assembly.console.colour.COLOUR
import com.assembly.console.colour.toColour
import com.assembly.entity.AssemblyCarEntity
import com.assembly.entity.Car
import com.assembly.line.AssemblyLine
import com.assembly.line.AssemblyStation
import com.assembly.log.getLogger
import kotlin.random.Random

data class CyberTruckAssembly(
	val trackingColour: COLOUR,
	// expected
	val expectedBatteries: Int,
	val expectedTintedWindows: Boolean,
	val expectedBulletProofWindows: Boolean,
	// actual
	val actualBatteries: Int = 0,
	val actualTintedWindows: Boolean = false,
	val actualBulletProofWindows: Boolean = false,
	val actualFunctioningWheels: Boolean = false,
	val actualWorkingConsole: Boolean = false,
	val actualUptoDateSoftware: Boolean = false,
) : AssemblyCarEntity

data class CyberTruckCar(
	val assembly: CyberTruckAssembly,
	val trackingColour: COLOUR,
	val hasTintedWindows: Boolean,
	val batteries: Int,
	val hasFunctioningWheels: Boolean,
	val hasBulletProofWindows: Boolean,
	val hasWorkingConsole: Boolean,
	val hasUptoDateSoftware: Boolean
) : Car

class CyberTruckAssemblyLine : com.assembly.entity.AssemblyLine<CyberTruckAssembly, CyberTruckCar> {

	companion object {
		val log = getLogger()
	}

	val line = AssemblyLine<CyberTruckAssembly, CyberTruckCar>(this.javaClass.simpleName)

	// Add Mechanical Assembly Station
	val assemblyMechanical = line + AssemblyStation("Assembly Mechanical") {

		var config = it

		// add some wheels to the CyberTruck
		// simulate ~0.1% failure rate
		if (Random.nextInt(1000) > 1) {
			log.info("Adding 4 Wheels".toColour(it.trackingColour))
			config = config.copy(actualFunctioningWheels = true)
		}

		// add some tinted windows to the CyberTruck if expected
		// simulate ~1% failure rate
		if (it.expectedTintedWindows && Random.nextInt(10) > 1) {
			log.info("Adding Tinted Windows".toColour(it.trackingColour))
			config = config.copy(actualTintedWindows = true)
		}

		// add some battery packs
		// simulate dead battery cells
		val batteryCount = if (Random.nextBoolean()) {
			config.expectedBatteries + Random.nextInt(10)
		} else {
			config.expectedBatteries - Random.nextInt(10)
		}
		config = config.copy(actualBatteries = batteryCount)
		log.info("Adding $batteryCount Batteries".toColour(it.trackingColour))

		config
	}

	// Add Internal Assembly Station
	val assemblyInternal = assemblyMechanical + AssemblyStation("Assembly Interior") {

		var config = it

		// add CyberTruck console
		// simulate failure rate of ~0.1%
		if (Random.nextInt(1000) > 1) {
			log.info("Adding Console".toColour(it.trackingColour))
			config = config.copy(actualWorkingConsole = true)
		}

		config
	}

	val build = assemblyInternal + AssemblyStation("Build") {
		log.info("Building CyberTruck".toColour(it.trackingColour))
		CyberTruckCar(
			assembly = it,
			trackingColour = it.trackingColour,
			hasTintedWindows = it.actualTintedWindows,
			batteries = it.actualBatteries,
			hasFunctioningWheels = it.actualFunctioningWheels,
			hasBulletProofWindows = it.actualBulletProofWindows,
			hasWorkingConsole = it.actualWorkingConsole,
			hasUptoDateSoftware = false
		)
	}
	val softwareUpdate = build + AssemblyStation("Software Update") {
		log.info("Updating software".toColour(it.trackingColour))
		it.copy(
			hasUptoDateSoftware = Random.nextInt(100) > 1
		)
	}
	val qa = softwareUpdate + AssemblyStation("QA", -1) {
		it
	}

	override suspend fun produce(assembly: CyberTruckAssembly): CyberTruckCar {
		log.info(line.toString().toColour(assembly.trackingColour))
		return line.process(assembly)
	}


}
