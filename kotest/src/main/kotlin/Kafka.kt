interface Kafka {
  suspend fun publish(feed: String): String
}

fun kafka(): Kafka = object : Kafka {
  override suspend fun publish(feed: String): String {
    return feed
  }
}