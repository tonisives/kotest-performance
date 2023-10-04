import io.kotest.core.config.AbstractProjectConfig

object KotestProject : AbstractProjectConfig() {
  // configurable values: https://kotest.io/docs/framework/project-config.html
  override val parallelism = 5
}