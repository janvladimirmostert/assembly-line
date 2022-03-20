package com.assembly.process

import com.assembly.operation.Activity

fun interface AssemblyInterior : Activity {
	override suspend fun execute() {
		assemblyInterior()
	}
	suspend fun assemblyInterior()
}