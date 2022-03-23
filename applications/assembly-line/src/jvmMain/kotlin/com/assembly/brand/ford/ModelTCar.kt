package com.assembly.brand.ford

import com.assembly.console.colour.COLOUR
import com.assembly.entity.Car

/**
 * A Ford Model T Car
 */
data class ModelTCar(
	val assembly: ModelTAssembly,
	val trackingColour: COLOUR,
	val interiorAssembled: Boolean,
	val mechanicAssembled: Boolean,
	val painted: Boolean,
	val polished: Boolean = false,
) : Car
