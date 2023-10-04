import env.Dependencies
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.runs
import io.mockk.unmockkStatic
import metrics.MetricsApp
import metrics.PrometheusServiceSpring
import org.springframework.boot.SpringApplication

class AppTest : DescribeSpec({
  coroutineTestScope = true

  val prometheus = mockk<PrometheusServiceSpring> {
    coEvery { start() } just runs
  }

  it("connects to feeds") {
    val deps = mockk<Dependencies>().also {
      every { it.log } returns mockk(relaxed = true)
      every { it.prometheus } returns prometheus
    }

    mockkStatic(SpringApplication::class)
    every { SpringApplication.run(MetricsApp::class.java) } returns mockk()

    app(deps).start()

    coVerifyOrder {
      prometheus.start()
    }

    unmockkStatic(SpringApplication::class)
  }
})