package application

import event.producer.JsonProducer


fun main(args: Array<String>) {
    val jsonProducer = JsonProducer("kafka:9092", "orders-topic")

    while (true) {
        jsonProducer.produce()
        Thread.sleep(3000L)
    }
}