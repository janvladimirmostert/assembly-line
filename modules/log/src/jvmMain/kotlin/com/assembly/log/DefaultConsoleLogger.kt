package com.assembly.log

import com.assembly.console.colour.COLOUR
import com.assembly.console.colour.COLOUR.*
import com.assembly.console.colour.toColour
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.function.Supplier

class DefaultConsoleLogger(name: String) : System.Logger {

	private val internalName = name

	companion object {
		fun logNow(
			loggerName: String,
			level: System.Logger.Level,
			text: String,
			throwable: Throwable? = null
		) {

			val levelCode = when (level) {
				System.Logger.Level.ALL -> "ALL"
				System.Logger.Level.TRACE -> "TRC"
				System.Logger.Level.DEBUG -> "DEB".toColour(ANSI_WHITE_BACKGROUND, ANSI_BLACK)
				System.Logger.Level.INFO -> "INF".toColour(ANSI_CYAN_BACKGROUND, ANSI_BLACK)
				System.Logger.Level.WARNING -> "WAR".toColour(ANSI_YELLOW_BACKGROUND, ANSI_BLACK)
				System.Logger.Level.ERROR -> "ERR".toColour(ANSI_RED_BACKGROUND, ANSI_WHITE)
				System.Logger.Level.OFF -> "OFF"
			}

			if (throwable == null) {
				println(
					listOf(
						levelCode,
						DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSS").format(LocalDateTime.now()),
						Thread.currentThread().name.toColour(ANSI_GREEN),
						loggerName.toColour(ANSI_PURPLE),
						text
					).joinToString(separator = " ")
				)
			}
		}
	}

	/**
	 * Returns the name of this logger.
	 *
	 * @return the logger name.
	 */
	override fun getName(): String = internalName

	/**
	 * Checks if a message of the given level would be logged by
	 * this logger.
	 *
	 * @param level the log message level.
	 * @return `true` if the given log message level is currently
	 * being logged.
	 *
	 * @throws NullPointerException if `level` is `null`.
	 */
	override fun isLoggable(level: System.Logger.Level?): Boolean {
		// TODO: allow configuration of log levels
		return true
	}

	/**
	 * Logs a localized message associated with a given throwable.
	 *
	 * If the given resource bundle is non-`null`,  the `msg`
	 * string is localized using the given resource bundle.
	 * Otherwise the `msg` string is not localized.
	 *
	 * @param level the log message level.
	 * @param bundle a resource bundle to localize `msg`; can be
	 * `null`.
	 * @param msg the string message (or a key in the message catalog,
	 * if `bundle` is not `null`); can be `null`.
	 * @param thrown a `Throwable` associated with the log message;
	 * can be `null`.
	 *
	 * @throws NullPointerException if `level` is `null`.
	 */
	override fun log(level: System.Logger.Level?, bundle: ResourceBundle?, msg: String?, thrown: Throwable?) {
		logNow(
			loggerName = name,
			level = level ?: System.Logger.Level.ALL,
			text = msg ?: "",
			throwable = thrown
		)
	}

	/**
	 * Logs a message with resource bundle and an optional list of
	 * parameters.
	 *
	 * If the given resource bundle is non-`null`,  the `format`
	 * string is localized using the given resource bundle.
	 * Otherwise the `format` string is not localized.
	 *
	 * @param level the log message level.
	 * @param bundle a resource bundle to localize `format`; can be
	 * `null`.
	 * @param format the string message format in [ ] format, (or a key in the message
	 * catalog if `bundle` is not `null`); can be `null`.
	 * @param params an optional list of parameters to the message (may be
	 * none).
	 *
	 * @throws NullPointerException if `level` is `null`.
	 */
	override fun log(level: System.Logger.Level?, bundle: ResourceBundle?, format: String?, vararg params: Any?) {
		TODO("Implement this logger")
	}

	override fun log(level: System.Logger.Level?, msg: String?) {
		logNow(
			loggerName = name,
			level = level ?: System.Logger.Level.ALL,
			text = msg ?: "",
			throwable = null
		)
	}

	override fun log(level: System.Logger.Level?, msgSupplier: Supplier<String>?) {
		TODO("Implement this logger")
	}

	override fun log(level: System.Logger.Level?, format: String?, vararg params: Any?) {
		TODO("Implement this logger")
	}

	override fun log(level: System.Logger.Level?, obj: Any?) {
		logNow(
			loggerName = name,
			level = level ?: System.Logger.Level.ALL,
			text = "$obj",
			throwable = null
		)
	}

	override fun log(level: System.Logger.Level?, msg: String?, thrown: Throwable?) {
		logNow(
			loggerName = name,
			level = level ?: System.Logger.Level.ALL,
			text = msg ?: "",
			throwable = thrown
		)
	}

	override fun log(level: System.Logger.Level?, msgSupplier: Supplier<String>?, thrown: Throwable?) {
		TODO("Implement this logger")
	}

}