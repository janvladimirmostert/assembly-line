package com.assembly.log

import java.lang.invoke.MethodHandles

/**
 * Whenever you need a logger, simply call:
 * val log = getLogger()
 * and it will automatically insert the class name
 */
@Suppress("NOTHING_TO_INLINE")
inline fun getLogger(): Logger {
	return Logger(name = MethodHandles.lookup().lookupClass().name)
}

/**
 * Logger class wraps the JDK9 System Logger to avoid having boilerplate
 *
 * @param name: name of the logger
 */
class Logger(val name: String) {

	// TODO: the correct way to get the logger is via System.getLogger(name)
	//    This allows for plugging in different Loggers similar to how SLF4j
	//    works, but since JDK9, Java has a default System.Logger.
	//    For this to work, the DefaultLoggerFinder needs to be setup to
	//    return the DefaultConsoleLogger and the DefaultLoggerFinder needs
	//    to be wired in via src/main/java/module-info.java
	//    See: https://cr.openjdk.java.net/~hannesw/javadoc-search-page/api.05/java.base/java/lang/System.LoggerFinder.html
	//private val systemLogger = System.getLogger(name)

	// due to issues getting module-info.java to behave correctly, we're temporarily hardcoding the logger here
	private val systemLogger = DefaultConsoleLogger(name)

	fun trace(text: Any?) {
		systemLogger.log(System.Logger.Level.TRACE, text ?: "")
	}

	fun debug(text: Any?) {
		systemLogger.log(System.Logger.Level.DEBUG, text ?: "")
	}

	fun info(text: Any?) {
		systemLogger.log(System.Logger.Level.INFO, text ?: "")
	}

	fun warn(text: Any?) {
		systemLogger.log(System.Logger.Level.WARNING, text ?: "")
	}

	fun error(text: String?) {
		systemLogger.log(System.Logger.Level.ERROR, text ?: "")
	}

	fun error(error: Throwable?) {
		if (error?.message != null) {
			systemLogger.log(System.Logger.Level.ERROR, error.message, error)
		} else {
			systemLogger.log(System.Logger.Level.ERROR, error)
		}
	}

	fun error(text: Any?, error: Throwable?) {
		systemLogger.log(System.Logger.Level.ERROR, text?.toString() ?: "", error)
	}
}