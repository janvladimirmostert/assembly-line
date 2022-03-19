package com.assembly.entity

import com.assembly.processes.Build
import com.assembly.processes.Paint

interface AssemblyCarEntity : Paint, Build {

	override suspend fun paint()
	override suspend fun build()

	override suspend fun execute() {
		TODO("Not yet implemented")
	}

}