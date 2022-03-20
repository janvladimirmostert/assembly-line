package com.assembly.process

import com.assembly.operation.Activity

fun interface Polish : Activity {
	override suspend fun execute() {
		polish()
	}
	suspend fun polish()
}