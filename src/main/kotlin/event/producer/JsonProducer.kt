package event.producer

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.util.StdDateFormat
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import model.Order
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import org.slf4j.LoggerFactory
import java.util.*

class JsonProducer(
        brokersUri: String,
        private val topic: String
) {

    private val kafkaProducer: KafkaProducer<String, String>
    private val logger = LoggerFactory.getLogger(this::class.java)

    private val jsonMapper = ObjectMapper().apply {
        registerKotlinModule()
        disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        dateFormat = StdDateFormat()
    }

    init {
        val properties = Properties()
        properties["bootstrap.servers"] = brokersUri
        properties["key.serializer"] = StringSerializer::class.java
        properties["value.serializer"] = StringSerializer::class.java
        properties["value.serializer"] = StringSerializer::class.java
        kafkaProducer = KafkaProducer(properties)
    }

    fun produce() {
        val fakeOrder = jsonMapper.writeValueAsString(Order.getFakeOrder())
        logger.info("Sending data: $fakeOrder")
        val futureResult = kafkaProducer.send(ProducerRecord<String, String>(topic, fakeOrder))
        logger.info("Sent")
        futureResult.get()
        logger.info("Writing acknowledged")
    }
}