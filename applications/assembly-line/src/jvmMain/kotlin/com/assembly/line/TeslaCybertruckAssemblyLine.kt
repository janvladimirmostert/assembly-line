package com.assembly.line

import com.assembly.console.colour.COLOUR.ANSI_PURPLE
import com.assembly.console.colour.toColour
import com.assembly.log.getLogger
import com.assembly.process.AssemblyInterior
import com.assembly.process.AssemblyMechanich

class TeslaCybertruckAssemblyLine : AssemblyLine {

	companion object {
		val log = getLogger()
	}

	override val activities = listOf(
		AssemblyMechanich() {
			it.blah()
			log.info("welding car together".toColour(ANSI_PURPLE))
		},
		AssemblyInterior {
			log.info("installing touch screen")
		}

	)
}