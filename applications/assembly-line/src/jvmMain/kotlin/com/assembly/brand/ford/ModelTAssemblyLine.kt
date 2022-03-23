package com.assembly.brand.ford

import com.assembly.console.colour.toColour
import com.assembly.entity.AssemblyLine
import com.assembly.line.AssemblyChain
import com.assembly.line.AssemblyRedirect
import com.assembly.line.AssemblyStation
import com.assembly.log.getLogger
import kotlin.random.Random

/**
 * Assembly line for the Ford Model T
 *
 * @param chain: chain of assembly operations
 */
class ModelTAssemblyLine(
	private val chain: AssemblyChain<ModelTAssembly, ModelTCar> = AssemblyChain(this::class.java.simpleName),
) : AssemblyLine<ModelTAssembly, ModelTCar> {

	companion object {
		val log = getLogger()
	}

	// add paint station to the chain
	private val paint = chain + AssemblyStation("Paint") {
		it.paint()
		it
	}

	// add mechanic assembly station to the chain
	private val assemblyMechanic = paint + AssemblyStation("AssemblyMechanich") {
		it.assemblyMechanic()
		it
	}

	// add assembly interior station to the chain
	private val assemblyInterior = assemblyMechanic + AssemblyStation("AssemblyInterior") {
		it.assemblyInterior()
		it
	}

	// add build station to the chain
	private val build = assemblyInterior + AssemblyStation("Build") {
		it.build()
	}

	// add a QA step to the chain
	private val qa = build + AssemblyStation("QA", -1) {
		if (Random.nextInt(100) < 20) {
			log.info("Quality Check Failed, rebuilding ...".toColour(it.trackingColour))
			return@AssemblyStation AssemblyRedirect(
				station = paint,
				it.assembly
			)
		}
		return@AssemblyStation it
	}

	// produce a car from the assembly configuration
	override suspend fun produce(assembly: ModelTAssembly): ModelTCar {
		log.info(chain.toString().toColour(assembly.trackingColour))
		return chain.process(assembly)
	}

	// expose the current AssemblyLine's chain to allow evolving it
	override fun expose(handler: (AssemblyChain<ModelTAssembly, ModelTCar>) -> Unit) {
		handler(this.chain)
	}

}