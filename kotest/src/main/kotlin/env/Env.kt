package env

import java.lang.System.getenv

@Suppress("PropertyName")
data class Env(
  val AWS_REGION: String = getenv("AWS_REGION") ?: "eu-west-1",
  val CHECKPOINT_TABLE_NAME: String = getenv("CHECKPOINT_TABLE_NAME") ?: "my-app-checkpoint",
  val CLUSTER_INSTANCE_TABLE_NAME: String = getenv("CLUSTER_INSTANCE_TABLE_NAME") ?: "my-app-cluster-instance",

  val SENTRY_DSN: String = getenv("SENTRY_DSN") ?: "https://public@sentry.example.com/1",
  val SENTRY_ENV: String = getenv("SENTRY_ENV") ?: "staging",

  val KAFKA_USER: String = getenv("KAFKA_USER") ?: "value",
  val KAFKA_PASSWORD: String = getenv("KAFKA_PASSWORD") ?: "value",
  val KAFKA_BROKERS: String = getenv("KAFKA_BROKERS") ?: "value",
  val KAFKA_GROUP_ID: String = getenv("KAFKA_GROUP_ID") ?: "value",
  val KAFKA_DESTINATION_TOPIC: String = getenv("KAFKA_DESTINATION_TOPIC") ?: "value",
  val KAFKA_SOURCE: String = getenv("KAFKA_SOURCE") ?: "value",
  val KAFKA_DLQ_TOPIC: String = getenv("KAFKA_DLQ_TOPIC") ?: "value",

  ) {
  init {
    println(systemInfo())
  }

  private fun systemInfo(): String {
    return """
      |System Info:
      | AWS_REGION: $AWS_REGION      
    """.trimIndent()
  }
}