package xyz.realmath

object ArrayTuples {
    fun rationalTuple(vararg ints: Int): ArrayTuple<Rational> {
        val rationals = Array(ints.size, { i -> Rational(ints[i]) })
        return ArrayTuple(*rationals)
    }

    fun polynomialTuple(vararg ints: Int): ArrayTuple<MultiVarPolynomial> {
        val pols = Array(ints.size, { i -> MultiVarPolynomial.valueOf(ints[i]) })
        return ArrayTuple(*pols)
    }
}
