@file:Suppress("TooGenericExceptionCaught", "MagicNumber")

import io.sentry.Sentry
import io.sentry.SentryLevel
import org.slf4j.Logger

fun withSentry(dsn: String, env: String, block: () -> Unit) {
  Sentry.init { options ->
    options.dsn = dsn
    options.tracesSampleRate = 1.0
    options.environment = env
  }

  try {
    block()
  } catch (e: Exception) {
    println("${e.stackTraceToString()}}")
    Sentry.captureException(e)
  }
}

fun sentryError(e: Exception, logger: Logger? = null) {
  logger?.error("", e)
  Sentry.captureException(e)
}

fun logSentryAndConsole(message: String, sentryLevel: SentryLevel, logger: Logger) {
  Sentry.captureMessage(message, sentryLevel)

  when (sentryLevel) {
    SentryLevel.DEBUG -> logger.debug(message)
    SentryLevel.INFO -> logger.info(message)
    SentryLevel.WARNING -> logger.warn(message)
    SentryLevel.ERROR -> logger.error(message)
    SentryLevel.FATAL -> logger.error(message)
  }
}

fun Logger.info(msg: () -> String) {
  if (this.isInfoEnabled) this.info(msg())
}

fun Logger.trace(msg: () -> String) {
  if (this.isTraceEnabled) this.trace(msg())
}

fun Logger.debug(msg: () -> String) {
  if (this.isDebugEnabled) this.debug(msg())
}
