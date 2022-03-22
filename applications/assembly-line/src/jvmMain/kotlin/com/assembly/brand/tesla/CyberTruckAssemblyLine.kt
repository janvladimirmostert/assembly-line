package com.assembly.brand.tesla

import com.assembly.console.colour.toColour
import com.assembly.entity.AssemblyLine
import com.assembly.line.AssemblyChain
import com.assembly.line.AssemblyRedirect
import com.assembly.line.AssemblyStation
import com.assembly.log.getLogger
import kotlin.random.Random

class CyberTruckAssemblyLine : AssemblyLine<CyberTruckAssembly, CyberTruckCar> {

	companion object {
		val log = getLogger()
	}

	private val chain = AssemblyChain<CyberTruckAssembly, CyberTruckCar>(this.javaClass.simpleName)

	// Add Mechanical Assembly Station
	private val assemblyMechanical = chain + AssemblyStation("Assembly Mechanical") {

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

		// adding bullet-proof windows to the CyberTruck if expected
		// simulate ~1% failure rate
		if (it.expectedBulletProofWindows && Random.nextInt(10) > 1) {
			log.info("Adding BulletProof Windows".toColour(it.trackingColour))
			config = config.copy(actualBulletProofWindows = true)
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
	private val assemblyInternal = assemblyMechanical + AssemblyStation("Assembly Interior") {

		var config = it

		// add CyberTruck console
		// simulate failure rate of ~0.1%
		if (Random.nextInt(1000) > 1) {
			log.info("Adding Console".toColour(it.trackingColour))
			config = config.copy(actualWorkingConsole = true)
		}

		config
	}

	private val build = assemblyInternal + AssemblyStation("Build") {
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

	private val softwareUpdate = build + AssemblyStation("Software Update") {
		log.info("Updating software".toColour(it.trackingColour))
		it.copy(
			hasUptoDateSoftware = Random.nextInt(100) > 1
		)
	}
	val qa = softwareUpdate + AssemblyStation("QA", -1) {

		var car = it

		// validate that bullet-proof windows are installed if it's expected
		if (car.assembly.expectedBulletProofWindows && !car.hasBulletProofWindows) {
			log.info("CyberTruck is missing BulletProof Windows, rebuilding ...".toColour(car.trackingColour))
			return@AssemblyStation AssemblyRedirect(
				station = assemblyMechanical,
				car.assembly
			)
		}

		// validate that the minimum battery size is installed
		if (car.assembly.expectedBatteries > car.batteries) {
			val missingBatteries = car.assembly.expectedBatteries - car.batteries
			log.info("CyberTruck is missing $missingBatteries batteries, rebuilding ...".toColour(car.trackingColour))
			return@AssemblyStation AssemblyRedirect(
				station = assemblyMechanical,
				car.assembly
			)
		}

		// validate that tinted windows are installed if requested
		if (car.assembly.expectedTintedWindows && !car.hasTintedWindows) {
			log.info("CyberTruck is missing Tinted Windows, rebuilding ...".toColour(car.trackingColour))
			return@AssemblyStation AssemblyRedirect(
				station = assemblyMechanical,
				car.assembly
			)
		}

		// validate that console is working
		if (!car.hasWorkingConsole) {
			log.info("CyberTruck console malfunction, rebuilding ...".toColour(car.trackingColour))
			return@AssemblyStation AssemblyRedirect(
				station = assemblyInternal,
				car.assembly
			)
		}

		// kick wheels to ensure they don't fall off
		if (!car.hasFunctioningWheels) {
			log.info("CyberTruck wheels not reliable, rebuilding ...".toColour(car.trackingColour))
			return@AssemblyStation AssemblyRedirect(
				station = assemblyMechanical,
				car.assembly
			)
		}

		// validate that software is up to date
		if (!car.hasUptoDateSoftware) {
			log.info("Software not up to date, QA is updating Software".toColour(car.trackingColour))
			car = car.copy(hasUptoDateSoftware = true)
		}

		if (Random.nextInt(100) > 20) {
			log.info("Quality Check Failed, rebuilding ...".toColour(car.trackingColour))
			return@AssemblyStation AssemblyRedirect(
				station = build,
				car.assembly
			)
		}

		car

	}

	override suspend fun produce(assembly: CyberTruckAssembly): CyberTruckCar {
		log.info(chain.toString().toColour(assembly.trackingColour))
		return chain.process(assembly)
	}

}
