package com.assembly.line

class AssemblyStation<I: Any?, O: Any?>(
	val name: String,
	val position: Int? = null,
	val partOf: AssemblyChain<*, *>? = null,
	val handler: (suspend (I) -> O),
) {
	override fun toString(): String {
		return "$name:$position"
	}

	operator fun <R> plus(station: AssemblyStation<O, R>): AssemblyStation<O, R> {
		if (this.partOf == null) {
			TODO("handle this")
		}
		return this.partOf.add(station)
	}

}
