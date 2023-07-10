package com.exchange.verifier

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class OrderTest {

    @Test
    fun `parseOrders should return list of OrderDTO objects for valid input`() {
        val input = listOf("10000,B,98,25500", "10001,S,100,500", "10002,B,102,1000")

        val order = Order()
        val result = order.parseOrders(input)

        assertEquals(3, result.size)

        val expected =
                listOf(
                        OrderDTO("10000", Side.B, 98, 25500),
                        OrderDTO("10001", Side.S, 100, 500),
                        OrderDTO("10002", Side.B, 102, 1000)
                )

        assertEquals(expected, result)
    }

    @Test
    fun `parseOrders should throw exception for invalid input format`() {
        val input =
                listOf(
                        "10000,B,98,25500",
                        "10001,S,100", // Invalid format, missing quantity
                        "10002,B,102,1000"
                )

        val order = Order()

        assertFailsWith<IllegalArgumentException> { order.parseOrders(input) }
    }

    @Test
    fun `parseOrders should return zero orderDTO for empty input`() {
        val input = emptyList<String>()

        val order = Order()

        val result = order.parseOrders(input)

        assertEquals(0, result.size)
    }
}
