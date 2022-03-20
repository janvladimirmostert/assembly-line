package com.assembly

import com.assembly.entity.AssemblyCarEntity
import com.assembly.line.TeslaCybertruckAssemblyLine
import com.assembly.operation.Activity
import com.assembly.process.*
import kotlinx.coroutines.delay


class AssemblyCarEntityIml : AssemblyCarEntity {

	override suspend fun paint() {
		println("painting!!!")
	}

	override suspend fun build() {
		println("building!!!")
	}


}


class AssemblyLine(
	vararg val activity: Activity,
) {

	suspend fun produce(e: AssemblyCarEntity) {
		this.activity.filter { it !is QualityCheck }.forEach { activity ->
			println(activity::class)
			activity.execute()
		}
		this.activity.filterIsInstance<QualityCheck>().forEach { activity ->
			println(activity as QualityCheck)
			activity.execute()
		}
	}
}


suspend fun main(args: Array<String>) {

	val recall =

	AssemblyLine(
		QualityCheck {
			println("checking car quality ")
		},
		Paint {
			delay(100)
			println("painting")
			delay(100)
		},
		Paint {
			delay(100)
			println("painting again")
			delay(100)
		},
		AssemblyMechanich {
			println("assembling mechanic")
		},

		Build {
			delay(21)
			println("building")
			delay(44)
		},
		AssemblyInterior {
			println("assembling interior")
		},

	).produce(AssemblyCarEntityIml())

	TeslaCybertruckAssemblyLine().produce(AssemblyCarEntityIml())


}
