package com.assembly.brand.tesla

import com.assembly.console.colour.COLOUR
import com.assembly.entity.Assembly
import com.assembly.entity.Car
import com.assembly.line.AssemblyLine
import com.assembly.line.AssemblyStation
import com.assembly.log.getLogger

data class CyberTruckAssembly(
	val tintedWindows: Boolean = true
) : Assembly

data class CyberTruckCar(
	val tintedWindows: Boolean = true
) : Car

class CyberTruckAssemblyLine : com.assembly.entity.AssemblyLine<CyberTruckAssembly, CyberTruckCar> {

	private val line = AssemblyLine<CyberTruckAssembly, CyberTruckCar>(this.javaClass.simpleName)

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

		line.process(assembly)

		return CyberTruckCar()
	}


}
