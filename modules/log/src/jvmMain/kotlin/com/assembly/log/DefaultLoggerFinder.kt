package com.assembly.log

class DefaultLoggerFinder : System.LoggerFinder() {
	/**
	 * Returns an instance of [Logger]
	 * for the given `module`.
	 *
	 * @param name the name of the logger.
	 * @param module the module for which the logger is being requested.
	 *
	 * @return a [logger][Logger] suitable for use within the given
	 * module.
	 * @throws NullPointerException if `name` is `null` or
	 * `module` is `null`.
	 * @throws SecurityException if a security manager is present and its
	 * `checkPermission` method doesn't allow the
	 * `RuntimePermission("loggerFinder")`.
	 */
	override fun getLogger(name: String?, module: Module?): System.Logger {
		return DefaultConsoleLogger(name ?: "Unknown")
	}


}