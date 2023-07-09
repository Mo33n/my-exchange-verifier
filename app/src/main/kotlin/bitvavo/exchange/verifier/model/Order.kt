package bitvavo.exchange.verifier

data class Order(val id: String, val side: Side, val price: Int, var quantity: Int)
