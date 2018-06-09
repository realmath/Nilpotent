package xyz.realmath

/** Unital Ring.  */
interface UnitalRing<T : UnitalRing<T>> : AbelianGroup<T> {
    operator fun plus(i: Int): T

    operator fun times(multiplier: T): T

    operator fun times(i: Int): T

    fun one(): T
}

operator fun <T : UnitalRing<T>> UnitalRing<T>.minus(i: Int): T {
    return this + -i
}
