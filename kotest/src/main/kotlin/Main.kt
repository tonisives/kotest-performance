import env.Env
import env.dependencies

import kotlinx.coroutines.runBlocking

fun main() {
  val env = Env()

  withSentry(env.SENTRY_DSN, env.SENTRY_ENV) {
    runBlocking {
      val dependencies = dependencies(env)
      app(dependencies).start()
    }
  }
}