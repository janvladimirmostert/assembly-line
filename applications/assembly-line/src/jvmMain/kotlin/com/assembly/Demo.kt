package com.assembly

import com.assembly.console.colour.COLOUR.ANSI_GREEN
import com.assembly.console.colour.toColour
import com.assembly.brand.tesla.CyberTruckAssembly
import com.assembly.brand.tesla.CyberTruckAssemblyLine
import com.assembly.log.getLogger
import kotlinx.coroutines.runBlocking

object Demo {

	private val log = getLogger()


	@JvmStatic
	fun main(args: Array<String>) = runBlocking {

		log.info(javaClass.getResourceAsStream("/art.txt")?.use {
			String(it.readAllBytes()).toColour(ANSI_GREEN)
		})

		val car = CyberTruckAssemblyLine().produce(
			CyberTruckAssembly(tintedWindows = false)
		)

		println("====")
		println(car)
		println("====")



		Unit
	}

}


