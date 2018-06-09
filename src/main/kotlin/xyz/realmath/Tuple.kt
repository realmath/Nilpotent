package xyz.realmath

interface Tuple<T : Algebra<Rational, T>> : Iterable<T> {
    operator fun get(idx: Int): T

    operator fun plus(tuple: Tuple<out T>): Tuple<T>

    operator fun times(r: Rational): Tuple<T>

    operator fun times(t: T): Tuple<T>

    operator fun unaryMinus(): Tuple<T>

    operator fun minus(tuple: Tuple<out T>): Tuple<T> = this + -tuple

    operator fun div(r: Rational): Tuple<T> = this * r.reciprocal()

    val dim: Int

    override fun iterator() = object : Iterator<T> {
        private var idx = 0
        override fun hasNext() = idx < dim
        override fun next() = get(idx++)
    }
}
