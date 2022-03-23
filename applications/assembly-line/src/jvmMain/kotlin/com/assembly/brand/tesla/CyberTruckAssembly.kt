package com.assembly.brand.tesla

import com.assembly.console.colour.COLOUR
import com.assembly.entity.AssemblyCarEntity

/**
 * Configuration for a Tesla CyberTruck assembly
 */
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