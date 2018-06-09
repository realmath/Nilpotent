package xyz.realmath

interface AbelianGroup<T : AbelianGroup<T>> {

    operator fun plus(t: T): T

    operator fun unaryMinus(): T

    fun zero(): T
}

val <T : AbelianGroup<T>> AbelianGroup<T>.isZero: Boolean
    get() = this == zero()

operator fun <T : AbelianGroup<T>> AbelianGroup<T>.minus(t: T): T = this + -t
