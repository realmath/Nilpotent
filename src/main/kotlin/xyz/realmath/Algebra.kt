package xyz.realmath

interface Algebra<F : Field<F>, T : Algebra<F, T>> : UnitalRing<T> {

    operator fun times(multiplier: F): T

    operator fun div(i: Int): T
}

operator fun <F : Field<F>, T : Algebra<F, T>> Algebra<F, T>.div(f: F): T {
    check(!f.isZero) { "f is zero" }
    return this * f.reciprocal()
}
