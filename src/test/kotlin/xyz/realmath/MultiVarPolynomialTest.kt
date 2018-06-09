package xyz.realmath

import xyz.realmath.MultiVarPolynomial.PolBuilder
import xyz.realmath.MultiVarPolynomial.Term
import kotlin.test.Test
import kotlin.test.assertEquals

class MultiVarPolynomialTest {
    @Test
    fun add() {
        val pol1 = PolBuilder()
                .add(Term.Builder().pow(0, 2).pow(1, 3).build(), Rational.ONE)
                .add(Term.Builder().pow(0, 3).pow(1, 2).build(), Rational(2))
                .add(Term.Builder().pow(0, 1).pow(1, 3).build(), Rational(3))
                .add(Term.Builder().pow(4, 1).build(), Rational.ONE)
                .add(Term.Builder().pow(6, 1).build(), Rational.ONE)
                .build()
        val pol2 = PolBuilder()
                .add(Term.Builder().pow(1, 3).build(), Rational.ONE)
                .add(Term.Builder().pow(0, 3).pow(1, 2).build(), Rational(5))
                .add(Term.Builder().pow(0, 1).pow(1, 3).build(), Rational(-3))
                .add(Term.Builder().pow(5, 1).build(), Rational.ONE)
                .add(Term.Builder().pow(6, 1).build(), -Rational.ONE)
                .build()
        val pol3 = PolBuilder()
                .add(Term.Builder().pow(1, 3).build(), Rational.ONE)
                .add(Term.Builder().pow(0, 2).pow(1, 3).build(), Rational.ONE)
                .add(Term.Builder().pow(0, 3).pow(1, 2).build(), Rational(7))
                .add(Term.Builder().pow(4, 1).build(), Rational.ONE)
                .add(Term.Builder().pow(5, 1).build(), Rational.ONE)
                .build()

        assertEquals(pol3, pol1 + pol2)
        assertEquals(pol3.hashCode().toLong(), (pol1 + pol2).hashCode().toLong())
    }

    @Test
    fun pow() {
        val pol = PolBuilder()
                .add(Term.Builder().build(), Rational.ONE)
                .add(Term.Builder().pow(0, 1).build(), Rational.ONE)
                .build()
        val expected = PolBuilder()
                .add(Term.Builder().build(), Rational.ONE)
                .add(Term.Builder().pow(0, 1).build(), Rational(2))
                .add(Term.Builder().pow(0, 2).build(), Rational.ONE)
                .build()
        assertEquals(expected, pol.pow(2))
    }

    @Test
    fun multiply() {
        val left = PolBuilder()
                .add(Term.Builder().build(), Rational.ONE)
                .add(Term.Builder().pow(0, 1).build(), Rational.ONE)
                .build()
        val right = PolBuilder()
                .add(Term.Builder().build(), Rational.ONE)
                .add(Term.Builder().pow(1, 1).build(), Rational.ONE)
                .build()
        val expected = PolBuilder()
                .add(Term.Builder().build(), Rational.ONE)
                .add(Term.Builder().pow(0, 1).build(), Rational.ONE)
                .add(Term.Builder().pow(1, 1).build(), Rational.ONE)
                .add(Term.Builder().pow(0, 1).pow(1, 1).build(), Rational.ONE)
                .build()
        assertEquals(expected, left * right)
    }
}
