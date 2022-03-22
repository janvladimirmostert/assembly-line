package com.assembly.brand.ford

import com.assembly.console.colour.COLOUR
import com.assembly.entity.Car

data class ModelTCarPolished(
	val assembly: ModelTAssembly,
	val trackingColour: COLOUR,
	val interiorAssembled: Boolean,
	val mechanicAssembled: Boolean,
	val painted: Boolean,
	val polished: Boolean,
) : Car {

}
