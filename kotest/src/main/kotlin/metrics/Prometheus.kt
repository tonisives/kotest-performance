package metrics

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

interface PrometheusServiceSpring {
  suspend fun start()
}

fun prometheusServiceSpring() = object : PrometheusServiceSpring {
  override suspend fun start() {
    SpringApplication.run(MetricsApp::class.java)
  }
}

@SpringBootApplication
open class MetricsApp