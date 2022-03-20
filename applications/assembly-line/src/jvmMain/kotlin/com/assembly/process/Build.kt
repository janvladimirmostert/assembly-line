package com.assembly.process

import com.assembly.operation.Activity

fun interface Build : Activity {

	override suspend fun execute() {
		build()
	}

	suspend fun build()

}