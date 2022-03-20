package com.assembly.line

import com.assembly.operation.Activity
import com.assembly.process.AssemblyInterior
import com.assembly.process.AssemblyMechanich
import com.assembly.process.Build


class TeslaCybertruckAssemblyLine : AssemblyLine {
	override val activities = listOf(
		AssemblyMechanich {
			println("welding car together")
		},
		AssemblyInterior {
			println("installing touch screen")
		}

	)
}