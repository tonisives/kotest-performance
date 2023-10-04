
import env.dependencies
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic

class MainTest : DescribeSpec({
  it("run main") {
    mockkStatic("AppKt")
    mockkStatic("env.DependenciesKt")
    every { dependencies(any()) } returns mockk()
    every { app(any()) } returns mockk {
      coEvery { start() } returns Unit
    }

    main()

    unmockkStatic("AppKt")
    unmockkStatic("env.DependenciesKt")
  }
})

