package com.assembly.line

data class AssemblyRedirect<I, O>(
	val station: AssemblyStation<I, O>,
	val data: I
)