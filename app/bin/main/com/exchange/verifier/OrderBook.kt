package com.exchange

enum class Side(val value: String) {
    B("BUY"),
    S("SELL")
}

class OrderBook {
    val bids: MutableList<OrderDTO> = mutableListOf()
    val asks: MutableList<OrderDTO> = mutableListOf()

    fun processOrders(orders: List<OrderDTO>): List<TradeDTO> {
        return orders.flatMap { processOrder(it) }
    }

    fun processOrder(order: OrderDTO): List<TradeDTO> {
        val trades = mutableListOf<TradeDTO>()

        when (order.side) {
            Side.B -> {
                val matchingAsks = asks.filter { it.price <= order.price }

                // Sort matched order by price-time-priority.
                // i.e (Trade with the LOWEST priced selling orders first, if found two orders of
                // same price then trade with earliest order first (lower orderId))
                val sortedAsks =
                        matchingAsks.sortedWith(compareBy<OrderDTO> { it.price }.thenBy { it.id })
                trades.addAll(processTrades(order, sortedAsks))
            }
            Side.S -> {
                val matchingBids = bids.filter { it.price >= order.price }

                // Sort matched order by price-time-priority.
                // i.e (Trade with the HIGEST priced buying orders first, if found two orders of
                // same price then trade with earliest order first (lower orderId))
                val sortedBids =
                        matchingBids.sortedWith(
                                compareByDescending<OrderDTO> { it.price }.thenBy { it.id }
                        )
                trades.addAll(processTrades(order, sortedBids))
            }
        }

        return trades
    }

    private fun processTrades(order: OrderDTO, matchingOrders: List<OrderDTO>): List<TradeDTO> {
        val trades = mutableListOf<TradeDTO>()
        var remainingQuantity = order.quantity

        for (matchingOrder in matchingOrders) {
            if (remainingQuantity == 0) break

            val tradeQuantity = minOf(matchingOrder.quantity, remainingQuantity)

            trades.add(TradeDTO(order.id, matchingOrder.id, matchingOrder.price, tradeQuantity))
            remainingQuantity -= tradeQuantity
            matchingOrder.quantity -= tradeQuantity

            // Remove the order if its fully consumed in this trade.
            if (matchingOrder.quantity == 0) {
                if (matchingOrder.side == Side.B) {
                    bids.remove(matchingOrder)
                } else {
                    asks.remove(matchingOrder)
                }
            }
        }

        // ASSUMPTION : We allow partial trade, so have to store remaining order quantity.
        if (remainingQuantity > 0) {
            val remainingOrder = OrderDTO(order.id, order.side, order.price, remainingQuantity)
            if (order.side == Side.B) {
                bids.add(remainingOrder)
            } else {
                asks.add(remainingOrder)
            }
        }

        return trades
    }

    fun printOrderBook() {

        val maxSize = maxOf(bids.size, asks.size)

        for (i in 0 until maxSize) {
            var bidLine = String.format("%18s", "")
            var askLine = String.format("%18s", "")

            val bid = bids.sortedWith(compareByDescending(OrderDTO::id)).getOrNull(i)
            val ask = asks.sortedWith(compareBy(OrderDTO::id)).getOrNull(i)

            /**
             * As per requirment and best of understanding, orderbook should be formated based of
             * this format: 000,000,000 000000 | 000000 000,000,000. If a value is too small to
             * cover the whole reserved area, it should be left padded with spaces.
             */
            if (bid != null) {
                bidLine =
                        "${String.format("%,-11d", bid.quantity)} ${String.format("%-6d", bid.price)}"
            }
            if (ask != null) {
                askLine =
                        "${String.format("%-6d", ask.price)} ${String.format("%,-11d", ask.quantity)}"
            }

            println("$bidLine | $askLine")
        }
    }
}
