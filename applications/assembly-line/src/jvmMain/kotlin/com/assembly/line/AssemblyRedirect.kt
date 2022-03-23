package com.assembly.line

/**
 * Allow redirection to another station in a chain
 */
data class AssemblyRedirect<I, O>(
	val station: AssemblyStation<I, O>,
	val data: I
)