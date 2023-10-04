import env.Dependencies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val APP_NAME = "my-app"

interface App {
  suspend fun start()
}

fun app(dep: Dependencies) = object : App {
  override suspend fun start() = withContext(Dispatchers.Default) {
    val log = dep.log

    log.info("my-app started")
    log.info("checking ddb tables")

    launch {
      dep.prometheus.start()
    }

    Unit
  }
}