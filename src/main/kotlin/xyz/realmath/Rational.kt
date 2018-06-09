package xyz.realmath

import java.math.BigInteger

class Rational private constructor(numer: BigInteger, denom: BigInteger) : Field<Rational> {

    private val numer: BigInteger
    private val denom: BigInteger

    init {
        check(denom != BigInteger.ZERO) { "denominator must not be zero" }

        var numer1 = numer.divide(numer.gcd(denom))
        var denom1 = denom.divide(numer.gcd(denom))

        if (denom1.signum() == -1) {
            numer1 = -numer1
            denom1 = -denom1
        }

        this.numer = numer1
        this.denom = denom1
    }

    constructor(i: Int) : this(BigInteger.valueOf(i.toLong()), BigInteger.ONE)

    override fun toString(): String {
        return if (denom == BigInteger.ONE) {
            numer.toString()
        } else {
            numer.toString() + ":" + denom.toString()
        }
    }

    override operator fun plus(t: Rational): Rational {
        val newDenom = denom * t.denom
        var newNumer = numer * t.denom

        newNumer += t.numer * denom

        return Rational(newNumer, newDenom)
    }

    override operator fun plus(i: Int): Rational {
        return this + Rational(i)
    }

    override fun zero() = ZERO

    override fun one() = ONE

    override operator fun times(multiplier: Rational): Rational {
        return Rational(numer * multiplier.numer, denom * multiplier.denom)
    }

    override operator fun times(i: Int): Rational {
        return this * Rational(i)
    }

    override operator fun div(i: Int): Rational {
        return this / Rational(i)
    }

    override fun reciprocal(): Rational {
        check(!isZero) { "zero reciprocal" }
        return Rational(denom, numer)
    }

    override operator fun unaryMinus(): Rational {
        return Rational(-numer, denom)
    }

    /** Power.  */
    fun pow(n: Int): Rational {
        if (n <= 0) {
            check(this != Rational.ZERO) { "undefined" }
        }
        if (n < 0) {
            return Rational.ONE / pow(-n)
        }
        var retVal = Rational.ONE
        for (i in 0 until n) {
            retVal *= this
        }
        return retVal
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Rational) return false

        if (numer != other.numer) return false
        if (denom != other.denom) return false

        return true
    }

    override fun hashCode(): Int {
        var result = numer.hashCode()
        result = 31 * result + denom.hashCode()
        return result
    }

    companion object {
        val ZERO = Rational(0)
        val ONE = Rational(1)

        fun factorial(n: Int): Rational {
            var retVal = Rational.ONE
            for (i in 1..n) {
                retVal *= i
            }
            return retVal
        }
    }
}
