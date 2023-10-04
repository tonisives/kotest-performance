import env.Env
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify
import io.sentry.Sentry
import io.sentry.SentryLevel
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class UtilsTest : DescribeSpec({
  var lazyInitialized = false
  fun resetLazyInitialized() = run { lazyInitialized = false }

  afterEach {
    resetLazyInitialized()
  }

  it("testWithSentry") {
    // test withSentry sends exception to sentry
    val env = Env()
    mockkStatic(Sentry::class)
    every { Sentry.captureException(any()) } returns mockk()

    withSentry(env.SENTRY_DSN, env.SENTRY_ENV) {
      throw IllegalArgumentException("test")
    }

    unmockkStatic(Sentry::class)
  }


  it("testLogWithSentry") {
    // just call the log methods
    val logger = mockk<Logger>(relaxed = true)

    mockkStatic(Sentry::class)
    every { Sentry.captureException(any()) } returns mockk()

    logSentryAndConsole("test", SentryLevel.DEBUG, logger)
    logSentryAndConsole("test", SentryLevel.INFO, logger)
    logSentryAndConsole("test", SentryLevel.WARNING, logger)
    logSentryAndConsole("test", SentryLevel.ERROR, logger)
    logSentryAndConsole("test", SentryLevel.FATAL, logger)

    unmockkStatic(Sentry::class)
  }


  it("testInfoLog") {
    val logger = mockk<Logger>(relaxed = true)
    logger.info { "test" }
    every { logger.isInfoEnabled } returns true
    logger.info { "test" }
    verify(exactly = 1) { logger.info(any()) }
  }


  it("testDebugLog") {
    val logger = mockk<Logger>(relaxed = true)
    logger.debug { "test" }
    every { logger.isDebugEnabled } returns true
    logger.debug { "test" }
    verify(exactly = 1) { logger.debug(any()) }
  }

  it("testTraceLog") {
    val logger = mockk<Logger>(relaxed = true)
    logger.trace { "test" }
    every { logger.isTraceEnabled } returns true
    logger.trace { "test" }
    verify(exactly = 1) { logger.trace(any()) }
  }

  class DontCall {
    var called = false

    init {
      lazyInitialized = true
    }

    override fun toString(): String {
      called = true
      return "dontCall"
    }
  }

  it("lazyTraceDoesNotCreateObject") {
    val logger = LoggerFactory.getLogger("my-app")

    logger.trace { "test ${DontCall()}" }


    lazyInitialized shouldBe false
    logger.isTraceEnabled shouldBe false
  }

  // normal trace calls functions

  it("normalTraceCreatesObject") {
    val logger = LoggerFactory.getLogger("my-app")

    logger.trace("test {}", DontCall())

    logger.isTraceEnabled shouldBe false
    lazyInitialized shouldBe true
  }
})
