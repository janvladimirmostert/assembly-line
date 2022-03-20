package com.assembly.process

import com.assembly.operation.Activity

fun interface Paint : Activity {

	override suspend fun execute() {
		paint()
	}

	suspend fun paint()

}