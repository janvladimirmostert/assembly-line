package com.assembly.brand.ford

import com.assembly.console.colour.COLOUR
import com.assembly.entity.Car

data class ModelTCar(
	val assembly: ModelTAssembly,
	val trackingColour: COLOUR,
) : Car
