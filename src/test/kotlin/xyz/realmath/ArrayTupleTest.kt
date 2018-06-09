package xyz.realmath

import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayTupleTest {
    @Test
    fun dimZero() {
        val t = ArrayTuple<Rational>()
        assertEquals(0, t.dim.toLong())
    }

    @Test
    fun dimOne() {
        val t = ArrayTuple(Rational.ONE)
        assertEquals(1, t.dim.toLong())
    }

    @Test
    operator fun iterator() {
        val t = ArrayTuples.rationalTuple(1, 2, 3)
        var i = 1
        for (r in t) {
            assertEquals(Rational(i), r)
            i++
        }
    }

    @Test
    fun get() {
        val t1 = ArrayTuples.rationalTuple(1, 0, 1)
        assertEquals(Rational.ONE, t1[2])
    }

    @Test
    fun add() {
        val t = ArrayTuple<Rational>()
        assertEquals(t, t + t)

        val t1 = ArrayTuples.rationalTuple(1, 0, 1)
        val t2 = ArrayTuples.rationalTuple(0, 1, 1)
        val t3 = ArrayTuples.rationalTuple(1, 1, 2)

        assertEquals(t3, t1 + t2)
    }

    @Test(expected = IllegalStateException::class)
    fun addWrongDim() {
        val t1 = ArrayTuples.rationalTuple(1)
        val t2 = ArrayTuples.rationalTuple(0, 0)
        t1 + t2
    }

    @Test
    fun minus() {
        val t = ArrayTuple<Rational>()
        assertEquals(t, t - t)

        val t1 = ArrayTuples.rationalTuple(1, 0, 1)
        val t2 = ArrayTuples.rationalTuple(0, 1, 1)
        val t3 = ArrayTuples.rationalTuple(1, -1, 0)

        assertEquals(t3, t1 - t2)
    }

    @Test
    fun multiply() {
        val t = ArrayTuple<Rational>()
        assertEquals(t, t as ArrayTuple<*> * Rational(3))

        val t1 = ArrayTuples.rationalTuple(1, 0, 1)
        val t3 = ArrayTuples.rationalTuple(3, 0, 3)
        assertEquals(t3, t1 as ArrayTuple<*> * Rational(3))
    }

    @Test
    fun hashCodeTest() {
        val t1 = ArrayTuples.rationalTuple(1, 0, 1)
        val t2 = ArrayTuples.rationalTuple(1, 0, 1)

        assertEquals(t1.hashCode().toLong(), t2.hashCode().toLong())
    }
}
