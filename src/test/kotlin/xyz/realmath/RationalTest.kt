package xyz.realmath

import kotlin.test.Test
import kotlin.test.assertEquals

class RationalTest {
    @Test(expected = IllegalStateException::class)
    fun zeroDenum() {
        Rational(1) / 0
    }

    @Test
    fun equal() {
        val rat1 = Rational(6) / 8
        val rat2 = Rational(9) / 12

        assertEquals(rat1, rat2)
        assertEquals(rat1.hashCode().toLong(), rat2.hashCode().toLong())
        assertEquals(Rational.ONE, Rational(1))
    }

    @Test
    fun negate() {
        val rat1 = Rational(6) / -8
        val rat2 = Rational(6) / 8

        assertEquals(Rational.ZERO, rat1 + rat2)
        assertEquals(Rational.ZERO, rat2 + rat1)
    }

    @Test
    fun multiply() {
        val rat1 = Rational(6) / 8
        val rat2 = Rational(2) / 3
        val rat3 = Rational.ONE / 2

        assertEquals(rat3, rat1 * rat2)
    }

    @Test
    fun reciprocal() {
        val rat1 = Rational(6) / 8
        val rat2 = Rational(4) / 3

        assertEquals(rat2, Rational.ONE / rat1)
    }

    @Test
    fun subtract() {
        val rat1 = Rational(6) / 8
        val rat2 = Rational(4) / 3
        val rat3 = Rational(-7) / 12

        assertEquals(rat3, rat1 - rat2)
    }

    @Test
    fun divideBy() {
        val rat1 = Rational(6) / 8
        val rat2 = Rational(4) / 3
        val rat3 = Rational(9) / 16

        assertEquals(rat3, rat1 / rat2)
    }

    @Test
    fun toStringTest() {
        val rat = Rational(7) / -12

        assertEquals("-7:12", rat.toString())
    }

    @Test
    fun pow() {
        val r = Rational(2) / -3
        val v = Rational(-27) / 8
        assertEquals(v, r.pow(-3))
    }

    @Test
    fun factorial() {
        assertEquals(Rational.ONE, Rational.factorial(0))
        assertEquals(Rational.ONE, Rational.factorial(1))
        assertEquals(Rational(2), Rational.factorial(2))
        assertEquals(Rational(6), Rational.factorial(3))
        assertEquals(Rational(24), Rational.factorial(4))
    }
}
