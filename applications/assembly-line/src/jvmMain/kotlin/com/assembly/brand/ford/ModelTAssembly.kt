package com.assembly.brand.ford

import com.assembly.console.colour.COLOUR
import com.assembly.entity.AssemblyCarEntity
import com.assembly.process.AssemblyInterior
import com.assembly.process.AssemblyMechanich
import com.assembly.process.Build
import com.assembly.process.Paint

data class ModelTAssembly(
	val trackingColour: COLOUR,
) : AssemblyCarEntity, Paint, AssemblyMechanich, AssemblyInterior, Build {

	override suspend fun assemblyInterior() {
		TODO("Not yet implemented")
	}

	override suspend fun assemblyMechanic() {
		TODO("Not yet implemented")
	}

	override suspend fun build(): ModelTCar {
		TODO("Not yet implemented")
	}

	override suspend fun paint() {
		TODO("Not yet implemented")
	}

}