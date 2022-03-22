package com.assembly.brand.ford

import com.assembly.console.colour.COLOUR
import com.assembly.console.colour.toColour
import com.assembly.entity.AssemblyCarEntity
import com.assembly.log.getLogger
import com.assembly.process.AssemblyInterior
import com.assembly.process.AssemblyMechanich
import com.assembly.process.Build
import com.assembly.process.Paint
import kotlinx.coroutines.delay
import kotlin.random.Random

data class ModelTAssembly(
	val trackingColour: COLOUR,
) : AssemblyCarEntity, Paint, AssemblyMechanich, AssemblyInterior, Build {

	private var interiorAssembled = false
	private var mechanicAssembled = false
	private var painted = false

	companion object {
		private val log = getLogger()
	}

	override suspend fun assemblyInterior() {
		log.info("You get a steering wheel and seats ...".toColour(trackingColour))
		delay(150 + Random.nextInt(1500).toLong())
		interiorAssembled = true
	}

	override suspend fun assemblyMechanic() {
		log.info("You only need wheels ...".toColour(trackingColour))
		delay(150 + Random.nextInt(2500).toLong())
		mechanicAssembled = true
	}

	override suspend fun build(): ModelTCar {
		log.info("Building car ...".toColour(trackingColour))
		delay(Random.nextInt(2500).toLong())
		return ModelTCar(
			assembly = this,
			trackingColour = this.trackingColour,
			interiorAssembled = this.interiorAssembled,
			mechanicAssembled = this.mechanicAssembled,
			painted = this.painted
		)
	}

	override suspend fun paint() {
		log.info(
			"You can have any colour, as long as it's black - apparently not Henry Ford".toColour(trackingColour)
		)
		log.info("Painting it black ... by hand ...".toColour(trackingColour))
		delay(Random.nextInt(5500).toLong())
		painted = true
	}

}