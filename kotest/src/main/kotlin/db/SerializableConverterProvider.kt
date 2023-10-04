package db

import software.amazon.awssdk.core.SdkBytes
import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter
import software.amazon.awssdk.enhanced.dynamodb.AttributeConverterProvider
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

// generic Serializable converter to Ddb AttributeValue

fun Serializable.toBytes(): ByteArray {
  val bos = ByteArrayOutputStream()
  val out = ObjectOutputStream(bos)
  out.writeObject(this)
  out.flush()
  return bos.toByteArray()
}

fun ByteArray.toInstance(): Serializable {
  val bis = ByteArrayInputStream(this)
  val ois = ObjectInputStream(bis)
  return ois.readObject() as Serializable
}

class SerializableConverterProvider : AttributeConverterProvider {
  private val converterCache: Map<EnhancedType<*>, AttributeConverter<*>> =
    mapOf(
      EnhancedType.of(Serializable::class.java) to SerializableConverter()
    )

  class SerializableConverter : AttributeConverter<Serializable> {
    override fun transformFrom(checkpoint: Serializable): AttributeValue {
      return AttributeValue.fromB(SdkBytes.fromByteArray(checkpoint.toBytes()))
    }

    override fun transformTo(attributeValue: AttributeValue): Serializable {
      val bytes = attributeValue.b()
        .asByteArray()
      return bytes.toInstance()
    }

    override fun type(): EnhancedType<Serializable> {
      return EnhancedType.of(Serializable::class.java)
    }

    override fun attributeValueType(): AttributeValueType {
      return AttributeValueType.M
    }
  }

  @Suppress("UNCHECKED_CAST")
  override fun <T : Any> converterFor(enhancedType: EnhancedType<T>): AttributeConverter<T> {
    val converter = converterCache[enhancedType]
    return converter as AttributeConverter<T>
  }
}