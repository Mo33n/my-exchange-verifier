package bitvavo.exchange.verifier

class OrderBook {
    private val bids: MutableList<Order> = mutableListOf()
    private val asks: MutableList<Order> = mutableListOf()

    fun getBidsSize(): Int {
        return bids.size
    }

    fun getAsksSize(): Int {
        return asks.size
    }

    fun processOrder(order: Order): List<Trade> {
        // println("processing order : ${order}")
        val trades = mutableListOf<Trade>()

        when (order.side) {
            Side.B -> {
                val matchingAsks = asks.filter { it.price <= order.price }

                // Sort matched order by price-time-priority.
                // i.e (Trade with the LOWEST priced selling orders first, if found two orders of
                // same price then trade with earliest order first (lower orderId))
                val sortedAsks =
                        matchingAsks.sortedWith(compareBy<Order> { it.price }.thenBy { it.id })
                trades.addAll(processTrades(order, sortedAsks))
            }
            Side.S -> {
                val matchingBids = bids.filter { it.price >= order.price }

                // Sort matched order by price-time-priority.
                // i.e (Trade with the HIGEST priced buying orders first, if found two orders of
                // same price then trade with earliest order first (lower orderId))
                val sortedBids =
                        matchingBids.sortedWith(
                                compareByDescending<Order> { it.price }.thenBy { it.id }
                        )
                trades.addAll(processTrades(order, sortedBids))
            }
        }

        return trades
    }

    private fun processTrades(order: Order, matchingOrders: List<Order>): List<Trade> {
        val trades = mutableListOf<Trade>()
        var remainingQuantity = order.quantity

        // println("processTrades: Order: ${order}, matchingOrders: ${matchingOrders}")

        for (matchingOrder in matchingOrders) {
            if (remainingQuantity == 0) break

            val tradeQuantity = minOf(matchingOrder.quantity, remainingQuantity)

            trades.add(Trade(order.id, matchingOrder.id, matchingOrder.price, tradeQuantity))
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
            val remainingOrder = Order(order.id, order.side, order.price, remainingQuantity)
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

            val bid = bids.sortedWith(compareByDescending(Order::id)).getOrNull(i)
            val ask = asks.sortedWith(compareBy(Order::id)).getOrNull(i)

            if (bid != null) {
                bidLine =
                        "${String.format("%,9d", bid.quantity).trim().padStart(11)} ${String.format("%,6d", bid.price).trim().padStart(6)}"
            }
            if (ask != null) {
                askLine =
                        "${String.format("%6d", ask.price).trim().padStart(6)} ${String.format("%,9d", ask.quantity).trim().padStart(11)}"
            }

            println("$bidLine | $askLine")
        }
    }
}
