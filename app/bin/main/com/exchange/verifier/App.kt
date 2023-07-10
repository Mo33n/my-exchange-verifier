package com.exchange.verifier
fun main(args: Array<String>) {
    // Sanity check on input
    if (args.isEmpty()) {
        println("No input provided.")
        return
    }

    // Declare entities
    val order: Order = Order()
    val trade: Trade = Trade()
    val orderBook: OrderBook = OrderBook()

    try {
        // Validate Input format
        val input: List<String> = args[0].lines()

        // Parse input into OrderDTO
        val orders: List<OrderDTO> = order.parseOrders(input)

        // Process orders to find trades
        val trades: List<TradeDTO> = orderBook.processOrders(orders)

        // Print Trades
        trade.printTrades(trades)

        // Print Orders
        orderBook.printOrderBook()
    } catch (e: IllegalArgumentException) {
        println(e.message)
    }
}
