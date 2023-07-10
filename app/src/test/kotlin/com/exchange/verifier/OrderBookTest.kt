package com.exchange.verifier

import kotlin.test.Test
import kotlin.test.assertEquals

// TODO : Add more test cases cases
class OrderBookTest {
    @Test
    fun TradeSizeAndBookSizeShouldbeCorrect() {
        val orderBook = OrderBook()
        val trades = mutableListOf<TradeDTO>()
        trades.addAll(orderBook.processOrder(OrderDTO("1", Side.B, 99, 1000)))
        trades.addAll(orderBook.processOrder(OrderDTO("2", Side.S, 98, 100000)))
        trades.addAll(orderBook.processOrder(OrderDTO("3", Side.S, 97, 100000)))
        trades.addAll(orderBook.processOrder(OrderDTO("2", Side.S, 94, 120)))
        trades.addAll(orderBook.processOrder(OrderDTO("4", Side.S, 91, 500)))
        trades.addAll(orderBook.processOrder(OrderDTO("5", Side.S, 91, 20)))
        trades.addAll(orderBook.processOrder(OrderDTO("6", Side.B, 95, 100)))

        assertEquals(trades.size, 2)
        assertEquals(orderBook.bids.size, 0)
        assertEquals(orderBook.asks.size, 5)
    }

    @Test
    fun NoTradeShouldHappenIfNoPriceMatch() {
        val orderBook = OrderBook()
        val trades = mutableListOf<TradeDTO>()
        trades.addAll(orderBook.processOrder(OrderDTO("1", Side.B, 98, 25500)))
        trades.addAll(orderBook.processOrder(OrderDTO("2", Side.S, 105, 20000)))
        trades.addAll(orderBook.processOrder(OrderDTO("3", Side.S, 100, 500)))
        trades.addAll(orderBook.processOrder(OrderDTO("4", Side.S, 100, 10000)))
        trades.addAll(orderBook.processOrder(OrderDTO("5", Side.B, 99, 50000)))
        trades.addAll(orderBook.processOrder(OrderDTO("6", Side.S, 103, 100)))

        assertEquals(trades.size, 0)
        assertEquals(orderBook.bids.size, 2)
        assertEquals(orderBook.asks.size, 4)
    }

    fun TradeSizeAndBookSizeShouldbeCorrect_test2() {
        val orderBook = OrderBook()
        val trades = mutableListOf<TradeDTO>()
        trades.addAll(orderBook.processOrder(OrderDTO("1", Side.B, 98, 25500)))
        trades.addAll(orderBook.processOrder(OrderDTO("2", Side.S, 105, 20000)))
        trades.addAll(orderBook.processOrder(OrderDTO("3", Side.S, 100, 500)))
        trades.addAll(orderBook.processOrder(OrderDTO("4", Side.S, 100, 10000)))
        trades.addAll(orderBook.processOrder(OrderDTO("5", Side.B, 99, 50000)))
        trades.addAll(orderBook.processOrder(OrderDTO("6", Side.S, 103, 100)))

        assertEquals(trades.size, 4)
        assertEquals(orderBook.bids.size, 2)
        assertEquals(orderBook.asks.size, 1)
    }
}
