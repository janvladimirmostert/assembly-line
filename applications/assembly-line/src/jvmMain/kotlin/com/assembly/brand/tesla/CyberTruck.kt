package com.assembly.brand.tesla

import com.assembly.entity.Car
import com.assembly.line.AssemblyLine
import com.assembly.line.AssemblyStation
import com.assembly.log.getLogger

class CyberTruckCar : Car {


}

class CyberTruckAssembly : com.assembly.entity.AssemblyLine<CyberTruckAssembly, CyberTruckCar> {

	private val line = AssemblyLine<CyberTruckAssembly>(this.javaClass.simpleName)

	companion object {
		val log = getLogger()
	}

	init {
		val assemblyMec = line + AssemblyStation("Assembly Mechanical") {
			this
		}
		val assemblyInt = assemblyMec + AssemblyStation("Assembly Interior") {
			this
		}
		val build = assemblyInt + AssemblyStation("Build") {
			CyberTruckCar()
		}
		val qa = build + AssemblyStation("QA", -1) {
			this
		}
	}

	override suspend fun produce(assembly: CyberTruckAssembly): CyberTruckCar {
		println(line)
		return CyberTruckCar()
	}


}
