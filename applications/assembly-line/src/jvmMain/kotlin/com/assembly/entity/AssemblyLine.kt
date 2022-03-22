package com.assembly.entity

import com.assembly.line.AssemblyChain

interface AssemblyLine<I : AssemblyCarEntity, O : Car> {
	fun expose(handler: AssemblyChain<I, O>.() -> Unit) = run { }
	suspend fun produce(assembly: I): O
}