package com.assembly

import com.assembly.operation.Activity
import com.assembly.processes.Build
import com.assembly.processes.Paint

interface AssemblyCarEntity : Paint, Build {

	override suspend fun paint()
	override suspend fun build()

	override suspend fun execute() {
		TODO("Not yet implemented")
	}

}

class AssemblyCarEntityIml : AssemblyCarEntity {

	override suspend fun paint() {
		println("painting")
	}

	override suspend fun build() {
		println("building")
	}


}


class AssemblyLine(
	vararg val activity: Activity
) {
	suspend fun produce(e: AssemblyCarEntity) {
		this.activity.forEach { activity ->
			activity.execute()
		}

	}
}




object Demo {
	@JvmStatic
	fun main(args: Array<String>) {


		AssemblyLine(
			Paint { println("painting") },
			Build { println("building") }
		)




	}

}