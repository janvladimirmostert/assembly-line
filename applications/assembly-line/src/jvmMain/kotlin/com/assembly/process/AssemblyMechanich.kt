package com.assembly.process

import com.assembly.operation.Activity

fun interface AssemblyMechanich : Activity {
	override suspend fun execute() {
		assemblyMechanic()
	}
	suspend fun assemblyMechanic()
}