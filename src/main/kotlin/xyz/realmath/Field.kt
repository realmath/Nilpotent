package xyz.realmath

interface Field<T : Field<T>> : Algebra<T, T> {

    override operator fun times(multiplier: T): T

    fun reciprocal(): T
}

operator fun <T : Field<T>> T.div(f: T): T {
    check(!f.isZero) { "f is zero" }
    return this * f.reciprocal()
}

val <T : Field<T>> T.isOne: Boolean
    get() = this == this * this
