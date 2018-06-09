package xyz.realmath

import xyz.realmath.MultiVarPolynomial.PolBuilder
import xyz.realmath.MultiVarPolynomial.Term

object Polynomials {
    /** [X_0, X_1, ..., X_{n-1}]  */
    fun basePols(dim: Int): Tuple<MultiVarPolynomial> {
        check(dim >= 0) { "dim < 0" }

        val retVal = Array(dim, { i ->
            PolBuilder().add(Term.Builder().pow(i, 1).build(),
                    Rational.ONE).build()
        })

        return ArrayTuple(*retVal)
    }

    /** [X_n, X_{n+1}, ..., X_{2n-1}]  */
    fun basePols2(dim: Int): Tuple<MultiVarPolynomial> {
        check(dim >= 0) { "dim < 0" }

        val retVal = Array(dim, { i ->
            PolBuilder().add(Term.Builder().pow(i + dim, 1).build(),
                    Rational.ONE).build()
        })

        return ArrayTuple(*retVal)
    }

    fun zeroTuple(dim: Int): Tuple<MultiVarPolynomial> {
        val list = Array(dim, { MultiVarPolynomial.ZERO })
        return ArrayTuple(*list)
    }
}
