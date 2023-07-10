package com.exchange
class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    println(App().greeting)
    // println("main arguments  : ${args[0]} ${args[1]}  ${args[2]}")

    val orderBook = OrderBook()

    /* val input =
            """
        10000,B,98,25500900
        10000,B,100009,25500900
        10005,S,105,20000
        10001,S,100,500
        10002,S,100,10000
        10003,B,9999,500000
        10004,S,103,100
        10006,B,105,16000
        10006,S,1577,144565456
        10006,S,157997,144565456
        10006,S,157997,144565

    """.trimIndent() */

    /*val input =
            """
    10000,B,98,25500
    10005,S,105,200
    10001,S,100,500
    10002,S,100,10000
    10003,B,99,50000
    10004,S,103,100
    10006,B,105,16000
    10007,S,105,20000
    """.trimIndent()*/

    /*val input =
    """
    10001,S,98,101
    10002,B,99,6
    10000,S,98,100
    """.trimIndent()*/

    /*val input =
    """
    10000,B,99,1000
    10001,B,98,1200
    10002,B,99,500
    10003,S,101,2000
    10004,S,95,2000
    """.trimIndent()*/

    val input =
            """
            10000,B,98,25500
            10005,S,105,20000
            10001,S,100,500
            10002,S,100,10000
            10003,B,99,500
            10004,S,103,100
            """.trimIndent()

    // Sorted orders based on orderId
    val orders =
            input.lines()
                    .map {
                        val (id, side, price, quantity) = it.split(",")
                        Order(id, Side.valueOf(side), price.toInt(), quantity.toInt())
                    }
                    .sortedWith(compareBy(Order::id))

    // TODO : VALIDATION

    val trades = mutableListOf<Trade>()

    for (order in orders) {
        val newTrades = orderBook.processOrder(order)
        trades.addAll(newTrades)
    }

    trades.forEach { trade ->
        println("trade ${trade.aggressorId},${trade.restingId},${trade.price},${trade.quantity}")
    }

    orderBook.printOrderBook()
}
