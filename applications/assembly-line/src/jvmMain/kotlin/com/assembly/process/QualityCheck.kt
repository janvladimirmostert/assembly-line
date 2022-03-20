package com.assembly.process

import com.assembly.operation.Activity

fun interface QualityCheck: Activity {
	override suspend fun execute() {
		checkQuality()
	}
	suspend fun checkQuality()
}