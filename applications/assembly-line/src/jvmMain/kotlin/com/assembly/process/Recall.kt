package com.assembly.process

import com.assembly.operation.Activity

fun interface Recall : Activity {

	override suspend fun execute() {
		recall()
	}

	suspend fun recall()

}