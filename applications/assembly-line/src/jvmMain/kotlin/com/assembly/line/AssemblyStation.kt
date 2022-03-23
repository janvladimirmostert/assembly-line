package com.assembly.line

import kotlinx.coroutines.sync.Mutex

/**
 * An Assembly Station
 *
 * @param name: name of the statium, typically only used for console logging
 * @param position: position of the station, stations are ordered via this position
 *     0,1,5,14,99,-1
 *     negative indexes will always be at the end.
 */
class AssemblyStation<I : Any?, O : Any?>(
	val name: String,
	val position: Int? = null,
	val partOf: AssemblyChain<*, *>? = null,
	val handler: (suspend (I) -> O),
) {

	// used to lock the station so that only one process can use the station at a time
	val mutex = Mutex()

	override fun toString(): String {
		return "$name:$position"
	}

	// mutate the chain an assembly station belongs to by chaining a station to a previous station
	operator fun <R> plus(station: AssemblyStation<O, R>): AssemblyStation<O, R> {
		return this.partOf?.add(station) ?: throw Exception("AssemblyStation.partOf should never be null")
	}

}
