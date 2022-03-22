package com.assembly.brand.ford

import com.assembly.entity.Car
import com.assembly.process.AssemblyInterior
import com.assembly.process.AssemblyMechanich
import com.assembly.process.Build
import com.assembly.process.Paint

data class ModelTCar(
	val assembly: ModelTAssembly,
) : Car, Paint, AssemblyMechanich, AssemblyInterior, Build {

	override suspend fun paint() {
		TODO("Not yet implemented")
	}

	override suspend fun assemblyInterior() {
		TODO("Not yet implemented")
	}

	override suspend fun assemblyMechanic() {
		TODO("Not yet implemented")
	}

	override suspend fun build() {
		TODO("Not yet implemented")
	}

}
