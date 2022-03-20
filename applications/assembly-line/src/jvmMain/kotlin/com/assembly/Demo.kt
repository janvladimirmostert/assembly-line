package com.assembly

import com.assembly.console.colour.COLOUR.ANSI_GREEN
import com.assembly.console.colour.toColour
import com.assembly.entity.AssemblyCarEntity
import com.assembly.line.TeslaCybertruckAssemblyLine
import com.assembly.log.getLogger
import com.assembly.operation.Activity
import com.assembly.process.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


class AssemblyCarEntityIml : AssemblyCarEntity {

	companion object {
		private val log = getLogger()
	}

	override suspend fun paint() {
		log.info("painting!!!")
	}

	override suspend fun build() {
		log.info("building!!!")
	}
}


class AssemblyLine(
	vararg val activity: Activity,
) {

	companion object {
		val log = getLogger()
	}

	suspend fun produce(e: AssemblyCarEntity) {
		this.activity.filter { it !is QualityCheck }.forEach { activity ->
			log.info(activity::class)
			activity.execute()
		}
		this.activity.filterIsInstance<QualityCheck>().forEach { activity ->
			log.info(activity as QualityCheck)
			activity.execute()
		}
	}
}

object Demo {

	private val log = getLogger()

	@JvmStatic
	fun main(args: Array<String>) = runBlocking {

		log.info(javaClass.getResourceAsStream("/art.txt")?.use {
			String(it.readAllBytes()).toColour(ANSI_GREEN)
		})

		AssemblyLine(
			QualityCheck {
				log.info("checking car quality ")
			},
			Paint {
				delay(100)
				log.info("painting")
				delay(100)
			},
			Paint {
				delay(100)
				log.info("painting again")
				delay(100)
			},
			AssemblyMechanich {
				log.info("assembling mechanic")
			},

			Build {
				delay(21)
				log.info("building")
				delay(44)
			},
			AssemblyInterior {
				log.info("assembling interior")
			},

			).produce(AssemblyCarEntityIml())

		TeslaCybertruckAssemblyLine().produce(AssemblyCarEntityIml())


	}

}

