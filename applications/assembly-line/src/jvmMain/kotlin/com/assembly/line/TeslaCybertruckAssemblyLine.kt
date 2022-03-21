package com.assembly.line

import com.assembly.AssemblyLine
import com.assembly.Station
import com.assembly.entity.tesla.CyberTruck
import com.assembly.entity.tesla.CyberTruckAssembly
import com.assembly.log.getLogger

class TeslaCybertruckAssemblyLine : com.assembly.entity.AssemblyLine<CyberTruckAssembly, CyberTruck> {

	private val line = AssemblyLine<CyberTruckAssembly>()

	companion object {
		val log = getLogger()
	}

	init {
		val assemblyMec = line + Station("Assembly Mechanical") {
			this
		}
		val assemblyInt = assemblyMec + Station("Assembly Interior") {
			this
		}
		val build = assemblyInt + Station("Build") {
			CyberTruck()
		}
		val qa = build + Station("QA", -1) {
			this
		}
	}

	override suspend fun produce(assembly: CyberTruckAssembly): CyberTruck {


		println(line)

		TODO("TODO")

	}




}
