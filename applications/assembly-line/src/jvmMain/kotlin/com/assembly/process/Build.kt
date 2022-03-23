package com.assembly.process

import com.assembly.entity.Car

/**
 * SAM interface for allowing Build
 */
fun interface Build {
	suspend fun build(): Car
}