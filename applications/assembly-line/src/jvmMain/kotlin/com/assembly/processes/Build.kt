package com.assembly.processes

import com.assembly.operation.Activity

fun interface Build : Activity {

	override suspend fun execute() {
		build()
	}

	suspend fun build()

}