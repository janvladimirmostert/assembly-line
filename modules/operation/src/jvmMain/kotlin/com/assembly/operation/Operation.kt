package com.assembly.operation

interface Operation {
	val begin: Activity?
	val activities: List<Activity>
	val end: Activity?
}