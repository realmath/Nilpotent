package xyz.realmath

class ArrayTuple<T : Algebra<Rational, T>> : Tuple<T> {

    private val ts = ArrayList<T>()

    private val length: Int

    @SafeVarargs
    constructor(vararg ts: T) {
        this.length = ts.size

        for (t in ts) {
            checkNotNull(t) { "ts" }
            this.ts.add(t)
        }
    }

    constructor(ts: Iterable<T>) {
        for (t in ts) {
            checkNotNull(t) { "ts" }
            this.ts.add(t)
        }
        length = this.ts.size
    }

    override val dim get() = length

    override fun get(idx: Int): T {
        return ts[idx]
    }

    override operator fun plus(tuple: Tuple<out T>): Tuple<T> {
        check(tuple.dim == length) { "tuple.length != length" }

        val sums = ArrayList<T>()
        for (i in 0 until length) {
            sums.add(ts[i] + tuple.get(i))
        }

        return ArrayTuple(sums)
    }

    override operator fun unaryMinus() = ArrayTuple(ts.map { -it })

    override operator fun times(r: Rational) = ArrayTuple(ts.map { it * r })

    override operator fun times(t: T) = ArrayTuple(ts.map { it * t })

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other !is ArrayTuple<*>) {
            return false
        }
        val ts1 = other as ArrayTuple<*>?
        return ts == ts1!!.ts
    }

    override fun hashCode(): Int {
        return ts.hashCode()
    }
}
