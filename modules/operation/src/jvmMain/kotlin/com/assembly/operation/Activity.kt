package com.assembly.operation

import com.assembly.log.getLogger

interface Activity<I> {

	class ActivityState(
		var capacity: Int = 0,
		var occupied: Boolean = false
	) {

		companion object {
			val log = getLogger()
		}

		fun blah() {
			log.info("blah!!!!!!!!!!!!!!!!!!!!!1")
		}
	}

	suspend fun <O> execute(): O
}