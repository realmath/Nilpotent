package xyz.realmath

import xyz.realmath.MultiVarPolynomial.PolBuilder
import xyz.realmath.MultiVarPolynomial.Term
import kotlin.test.Test
import kotlin.test.assertEquals

class PolynomialsTest {
    @Test
    fun baseVector() {
        var pols = Polynomials.basePols(0)
        assertEquals(0, pols.dim.toLong())

        pols = Polynomials.basePols(3)
        assertEquals(
                PolBuilder().add(Term.Builder().pow(1, 1).build(), Rational.ONE).build(),
                pols[1])
    }

    @Test
    fun baseVector2() {
        var pols = Polynomials.basePols(0)
        assertEquals(0, pols.dim.toLong())

        pols = Polynomials.basePols(4)
        assertEquals(
                PolBuilder().add(Term.Builder().pow(3, 1).build(), Rational.ONE).build(),
                pols[3])
    }
}
