package com.assembly

import com.assembly.entity.AssemblyCarEntity
import com.assembly.operation.Activity
import com.assembly.processes.AssemblyInterior
import com.assembly.processes.AssemblyMechanich
import com.assembly.processes.Build
import com.assembly.processes.Paint
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
		this.activity.forEach { activity ->

			println(activity::class)
			println(activity is AssemblyCarEntity)

			activity.execute()
		}

	}
}


suspend fun main(args: Array<String>) {

	AssemblyLine(
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
		AssemblyInterior {
			println("assembling interior")
		},
		Build {
			delay(21)
			println("building")
			delay(44)
		}
	).produce(AssemblyCarEntityIml())


}
