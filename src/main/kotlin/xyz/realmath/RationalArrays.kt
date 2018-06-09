package xyz.realmath

object RationalArrays {

    fun base(length: Int, idx: Int): Array<Rational> {
        check(idx >= 0) { "idx < 0" }
        check(idx < length) { "idx >= length" }
        return Array(length, { i ->
            if (i == idx) {
                Rational.ONE
            } else {
                Rational.ZERO
            }
        })
    }
}
