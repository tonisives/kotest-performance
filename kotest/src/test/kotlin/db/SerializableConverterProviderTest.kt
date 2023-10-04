package db

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType
import java.io.Serializable

class SerializableConverterProviderTest : FreeSpec({
  "serialize" {
    val instance = object : Serializable {}
    val bytes = instance.toBytes()
    bytes.size shouldBe 72

    val provider = SerializableConverterProvider()
    val converter = SerializableConverterProvider.SerializableConverter()
    converter.type() shouldBe EnhancedType.of(Serializable::class.java)

    converter.attributeValueType() shouldBe AttributeValueType.M
    provider.converterFor(EnhancedType.of(Serializable::class.java)).attributeValueType() shouldBe AttributeValueType.M
  }
})
