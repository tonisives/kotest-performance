package metrics

import io.kotest.core.spec.style.FreeSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.springframework.boot.SpringApplication

class PrometheusServiceSpringTest : FreeSpec({
  coroutineTestScope = true

  "should run spring boot" {
    mockkStatic(SpringApplication::class)
    every { SpringApplication.run(MetricsApp::class.java) } returns mockk()
    val prometheus = prometheusServiceSpring()
    prometheus.start()

    MetricsApp()

    unmockkStatic(SpringApplication::class)
  }
})