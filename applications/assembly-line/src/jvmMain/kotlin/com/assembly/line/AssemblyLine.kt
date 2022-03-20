//package com.assembly.line
//
//import com.assembly.operation.Activity
//import com.assembly.process.QualityCheck
//
//interface AssemblyLine {
//
//	val activities: List<Activity<Any?>>
//
//	suspend fun produce(entity: Activity<Any?>) {
//		this.activities.filter { it !is QualityCheck }.forEach { activity ->
//			activity.execute()
//		}
//		this.activities.filterIsInstance<QualityCheck>().forEach { activity ->
//			activity.execute()
//		}
//	}
//
//}