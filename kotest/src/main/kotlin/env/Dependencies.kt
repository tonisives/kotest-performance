package env

import io.klogging.Klogger
import io.klogging.logger
import metrics.PrometheusServiceSpring
import metrics.prometheusServiceSpring
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient

data class Dependencies(
  val log: Klogger,
  val prometheus: PrometheusServiceSpring,
)

fun dependencies(env: Env): Dependencies {
  val log = logger("main")

  val (_, _) = getDdb(env)

  val prometheus = prometheusServiceSpring()


  return Dependencies(log, prometheus)
}

private fun getDdb(env: Env): Pair<DynamoDbEnhancedClient, DynamoDbClient> {
  val ddbLowClient = DynamoDbClient.builder()
    .region(Region.of(env.AWS_REGION))
    .build()
  val ddbClient = DynamoDbEnhancedClient.builder()
    .dynamoDbClient(ddbLowClient)
    .build()

  return Pair(ddbClient, ddbLowClient)
}