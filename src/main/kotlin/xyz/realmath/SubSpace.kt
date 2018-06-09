package xyz.realmath

class SubSpace private constructor(private val embeddingDim: Int, private val basis: Map<Tuple<Rational>, Int>) {

    val dim = basis.size

    init {
        check(embeddingDim >= 0) { "embeddingDim < 0" }
    }

    fun basis(): Set<Tuple<Rational>> = basis.keys

    operator fun contains(vector: Tuple<Rational>): Boolean {
        check(vector.dim == embeddingDim) { "vector.length != embedding dim" }
        // TODO: ZERO
        return reduce(vector) == vector as Tuple<out Rational> * Rational.ZERO
    }

    private fun reduce(vector: Tuple<Rational>): Tuple<Rational> {
        var retVal = vector
        basis.forEach { key, idx ->
            val v = retVal[idx]
            if (v != Rational.ZERO) {
                retVal -= key as Tuple<out Rational> * v
            }
        }
        return retVal
    }

    private fun spanWith(vector: Tuple<Rational>): SubSpace {
        var tmpVector = reduce(vector)

        // normalize vector
        var i = 0
        for (r in tmpVector) {
            if (r != Rational.ZERO) {
                tmpVector /= r
                break
            }
            i++
        }
        if (i == embeddingDim) {
            return this
        }

        // normalize old base vectors
        val newBasis = HashMap<Tuple<Rational>, Int>()
        newBasis.put(tmpVector, i)

        for (entry in basis.entries) {
            var t1 = entry.key
            val v = entry.key[i]
            if (v != Rational.ZERO) {
                t1 -= tmpVector as Tuple<out Rational> * v
            }
            newBasis.put(t1, entry.value)
        }
        return SubSpace(embeddingDim, newBasis)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other !is SubSpace) {
            return false
        }
        if (other.embeddingDim != embeddingDim) {
            return false
        }

        return basis.keys.all { other.contains(it) } && other.basis.keys.all { contains(it) }
    }

    override fun hashCode(): Int {
        var result = embeddingDim
        result = 31 * result + dim
        return result
    }

    companion object {
        fun span(vararg vectors: Tuple<Rational>): SubSpace {
            check(vectors.size > 0) { "require at least one vector" }
            var retVal = zeroDim(vectors[0].dim)
            retVal = retVal.spanWith(vectors[0])
            for (v in vectors) {
                checkNotNull(v) { "vector" }
                check(v.dim == retVal.embeddingDim) { "vectors with different dim" }
                retVal = retVal.spanWith(v)
            }
            return retVal
        }

        fun zeroDim(embeddingDim: Int): SubSpace {
            return SubSpace(embeddingDim, emptyMap())
        }
    }
}
