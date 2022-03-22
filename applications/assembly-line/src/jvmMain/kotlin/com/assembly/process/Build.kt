package com.assembly.process

import com.assembly.entity.Car

fun interface Build {
	suspend fun build(): Car
}