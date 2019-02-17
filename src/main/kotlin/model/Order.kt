package model

import com.github.javafaker.Faker
import java.util.*

data class Order(
        val id: UUID,
        val time: Date,
        val item: String,
        val amount: Int
) {
    companion object {

        fun getFakeOrder(): Order {
            val faker = Faker()
            return Order(
                    id = UUID.randomUUID(),
                    time = Date(),
                    item = faker.pokemon().name(),
                    amount = (faker.commerce().price().toDouble() * 100).toInt()

            )
        }
    }
}