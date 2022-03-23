package com.assembly.brand.tesla

import com.assembly.console.colour.COLOUR
import com.assembly.entity.Car

/**
 * A Tesla CyberTruck
 */
data class CyberTruckCar(
	val assembly: CyberTruckAssembly,
	val trackingColour: COLOUR,
	val hasTintedWindows: Boolean,
	val batteries: Int,
	val hasFunctioningWheels: Boolean,
	val hasBulletProofWindows: Boolean,
	val hasWorkingConsole: Boolean,
	val hasUptoDateSoftware: Boolean,
) : Car