package com.assembly.brand.ford

import com.assembly.console.colour.toColour
import com.assembly.entity.AssemblyLine
import com.assembly.line.AssemblyChain
import com.assembly.line.AssemblyStation
import com.assembly.log.getLogger

class ModelTAssemblyLine : AssemblyLine<ModelTAssembly, ModelTCar> {

	companion object {
		val log = getLogger()
	}

	private val chain = AssemblyChain<ModelTAssembly, ModelTCar>(this.javaClass.simpleName)

	// add paint station to the chain
	private val paint = chain + AssemblyStation("Paint") {
		//it.paint()
		it
	}

	// add mechanic assembly station to the chain
	private val assemblyMechanic = paint + AssemblyStation("AssemblyMechanich") {

	}

	// add assembly interior station to the chain
	private val assemblyInterior = assemblyMechanic + AssemblyStation("AssemblyInterior") {

	}

	// add build station to the chain
	private val build = assemblyInterior + AssemblyStation("Build") {

	}

	// add a QA step to the chain
	private val qa = build + AssemblyStation("QA", -1) {

	}

	override suspend fun produce(assembly: ModelTAssembly): ModelTCar {
		log.info(chain.toString().toColour(assembly.trackingColour))
		return chain.process(assembly)
	}

}