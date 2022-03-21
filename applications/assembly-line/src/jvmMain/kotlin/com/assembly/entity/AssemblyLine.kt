package com.assembly.entity

interface AssemblyLine<I, O> {
	suspend fun produce(assembly: I): O
}