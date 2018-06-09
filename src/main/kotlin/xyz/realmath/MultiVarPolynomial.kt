package xyz.realmath

class MultiVarPolynomial private constructor(private val monoids: Map<Term, Rational>) : Algebra<Rational, MultiVarPolynomial> {

    init {
        for (`val` in monoids.values) {
            check(`val` != Rational.ZERO) { "term with 0 coef" }
        }
    }

    override operator fun plus(t: MultiVarPolynomial): MultiVarPolynomial {
        val builder = PolBuilder()
        for ((key, value) in monoids) {
            builder.add(key, value)
        }

        for ((key, value) in t.monoids) {
            builder.add(key, value)
        }

        return builder.build()
    }

    override operator fun unaryMinus(): MultiVarPolynomial {
        return this * -Rational.ONE
    }

    override operator fun times(multiplier: MultiVarPolynomial): MultiVarPolynomial {
        val builder = PolBuilder()
        for ((key, value) in monoids) {
            for ((key1, value1) in multiplier.monoids) {
                builder.add(
                        key * key1,
                        value * value1)
            }
        }

        return builder.build()
    }

    override operator fun times(i: Int): MultiVarPolynomial {
        return this * Rational(i)
    }

    override operator fun plus(i: Int): MultiVarPolynomial {
        return this + valueOf(i)
    }

    override operator fun times(multiplier: Rational): MultiVarPolynomial {
        return this * PolBuilder().add(Term.Builder().build(), multiplier).build()
    }

    override operator fun div(i: Int): MultiVarPolynomial {
        return this / Rational(i)
    }

    override fun zero() = ZERO

    override fun one() = ONE

    fun pow(p: Int): MultiVarPolynomial {
        check(p >= 0) { "p < 0" }
        var retVal = ONE
        for (i in 0 until p) {
            retVal *= this
        }
        return retVal
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other !is MultiVarPolynomial) {
            return false
        }
        return monoids == other.monoids
    }

    override fun hashCode(): Int {
        return monoids.hashCode()
    }

    class PolBuilder {
        private val monoids: MutableMap<Term, Rational> = HashMap()

        private var built: Boolean = false

        fun add(term: Term, coef: Rational): PolBuilder {
            if (coef == Rational.ZERO) {
                return this
            }
            if (monoids.containsKey(term)) {
                val newVal: Rational = monoids[term]!! + coef
                if (newVal == Rational.ZERO) {
                    monoids.remove(term)
                } else {
                    monoids.put(term, newVal)
                }
            } else {
                monoids.put(term, coef)
            }
            return this
        }

        fun build(): MultiVarPolynomial {
            check(!built) { "built" }
            built = true
            return MultiVarPolynomial(monoids)
        }
    }

    class Term private constructor(private val map: Map<Int, Int>) {

        init {
            for ((key, value) in map) {
                check(key >= 0) { "varName < 0" }
                check(value > 0) { "degree <= 0" }
            }
        }

        fun degreeOf(i: Int): Int {
            return map[i] ?: 0
        }

        operator fun times(t: Term): Term {
            val newMap = HashMap(map)

            for ((key, value) in t.map) {
                newMap.put(key, (newMap[key] ?: 0) + value)
            }
            return Term(newMap)
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) {
                return true
            }
            if (other !is Term) {
                return false
            }
            return map == other.map
        }

        override fun hashCode(): Int {
            return map.hashCode()
        }

        class Builder {
            private val map = HashMap<Int, Int>()
            private var built: Boolean = false

            fun pow(`var`: Int, deg: Int): Builder {
                map.put(`var`, deg)
                return this
            }

            fun build(): Term {
                check(!built) { "built" }
                built = true
                return Term(map)
            }
        }
    }

    companion object {
        private val CONST_TERM = Term.Builder().build()
        val ZERO = MultiVarPolynomial(emptyMap())
        val ONE = MultiVarPolynomial(mapOf(CONST_TERM to Rational.ONE))

        fun valueOf(i: Int): MultiVarPolynomial {
            return valueOf(Rational(i))
        }

        fun valueOf(rational: Rational): MultiVarPolynomial {
            return PolBuilder().add(CONST_TERM, rational).build()
        }
    }
}
