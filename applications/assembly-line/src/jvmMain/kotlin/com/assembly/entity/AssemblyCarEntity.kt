package com.assembly.entity

import com.assembly.process.Build
import com.assembly.process.Paint

interface AssemblyCarEntity : Paint, Build {

	override suspend fun paint()
	override suspend fun build()

	override suspend fun execute() {
		TODO("Not yet implemented")
	}

}