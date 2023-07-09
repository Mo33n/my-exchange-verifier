package bitvavo.exchange.verifier

data class Trade(val aggressorId: String, val restingId: String, val price: Int, val quantity: Int)
