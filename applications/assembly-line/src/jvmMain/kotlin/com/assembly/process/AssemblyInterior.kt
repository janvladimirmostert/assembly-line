package com.assembly.process

/**
 * SAM interface for allowing interior assembly
 */
fun interface AssemblyInterior {
	suspend fun assemblyInterior()
}