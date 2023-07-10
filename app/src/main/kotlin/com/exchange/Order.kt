package com.exchange.verifier

data class OrderDTO(val id: String, val side: Side, val price: Int, var quantity: Int)

class Order {

    val regex = Regex("""^\d+,[BS],[1-9]\d*,[1-9]\d*$""")

    fun parseOrders(input: List<String>): List<OrderDTO> {
        val orders = mutableListOf<OrderDTO>()

        for (line in input) {
            if (!isValidInputFormat(line)) {
                throw IllegalArgumentException(
                        "Invalid input: ($line) \nYou can find valid input format in README.md"
                )
            }

            val (id, side, price, quantity) = line.split(",")
            orders.add(OrderDTO(id, Side.valueOf(side), price.toInt(), quantity.toInt()))
        }

        return orders.sortedBy(OrderDTO::id)
    }

    private fun isValidInputFormat(line: String): Boolean {
        return this.regex.matches(line.trim())
    }
}
