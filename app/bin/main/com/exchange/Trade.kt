package com.exchange

data class TradeDTO(
        val aggressorId: String,
        val restingId: String,
        val price: Int,
        val quantity: Int
)

class Trade() {
    fun printTrades(trades: List<TradeDTO>) {
        trades.forEach { trade ->
            println(
                    "trade ${trade.aggressorId},${trade.restingId},${trade.price},${trade.quantity}"
            )
        }
    }
}
