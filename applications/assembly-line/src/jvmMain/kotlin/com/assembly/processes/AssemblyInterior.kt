package com.assembly.processes

import com.assembly.operation.Activity

fun interface AssemblyInterior : Activity {
	override suspend fun execute() {
		assemblyInterior()
	}
	suspend fun assemblyInterior()
}