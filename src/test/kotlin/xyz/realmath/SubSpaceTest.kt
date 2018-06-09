package xyz.realmath

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class SubSpaceTest {

    @Test
    fun span() {
        val t1 = ArrayTuple(Rational.ZERO,
                Rational.ONE, Rational(2), Rational(3))
        val t2 = ArrayTuple(
                Rational.ZERO, Rational(2), Rational(4), Rational(6))
        val t3 = ArrayTuple(Rational.ONE,
                Rational.ONE, Rational(2), Rational(3))
        val t4 = ArrayTuple(Rational.ONE, Rational.ONE,
                Rational.ONE, Rational(3))
        val t5 = ArrayTuple(Rational.ONE, Rational.ONE,
                Rational.ONE, Rational.ONE)
        val t6 = ArrayTuple(
                Rational(7), Rational(-1), Rational(3), Rational(19))

        var s = SubSpace.span(t1)
        assertEquals(1, s.dim)

        s = SubSpace.span(t1, t2)
        assertEquals(1, s.dim)

        s = SubSpace.span(t1, t3)
        assertEquals(2, s.dim)

        s = SubSpace.span(t1, t3, t4)
        assertEquals(3, s.dim)

        s = SubSpace.span(t1, t3, t4, t5)
        assertEquals(4, s.dim)

        s = SubSpace.span(t1, t3, t4, t5, t6)
        assertEquals(4, s.dim)
    }

    @Test
    fun zerodim() {
        val s = SubSpace.zeroDim(3)
        assertEquals(0, s.dim)
    }

    @Test
    fun zeroEmbedding() {
        val s = SubSpace.zeroDim(0)
        assertTrue(s.contains(ArrayTuple()))
        assertEquals(0, s.dim)
        assertEquals(s.dim, s.basis().size)
    }

    @Test
    fun contains() {
        val t1 = ArrayTuple(
                Rational(0), Rational(1), Rational(2), Rational(3))
        val t2 = ArrayTuple(
                Rational(0), Rational(2), Rational(4), Rational(6))
        val t3 = ArrayTuple(
                Rational(1), Rational(1), Rational(2), Rational(3))
        val t4 = ArrayTuple(
                Rational(1), Rational(1), Rational(1), Rational(3))
        val t5 = ArrayTuple(
                Rational(1), Rational(1), Rational(1), Rational(1))
        val t6 = ArrayTuple(
                Rational(7), Rational(-1), Rational(3), Rational(19))

        var s = SubSpace.span(t1)
        assertTrue(s.contains(t2))
        for (t in s.basis()) {
            assertTrue(s.contains(t))
        }

        s = SubSpace.span(t1, t3)
        assertTrue(s.contains(t2))
        assertTrue(s.contains(t3))
        assertFalse(s.contains(t4))
        for (t in s.basis()) {
            assertTrue(s.contains(t))
        }

        s = SubSpace.span(t1, t2, t3, t4)
        assertFalse(s.contains(t5))
        for (t in s.basis()) {
            assertTrue(s.contains(t))
        }

        s = SubSpace.span(t1, t2, t3, t4, t5)
        assertTrue(s.contains(t6))
        for (t in s.basis()) {
            assertTrue(s.contains(t))
        }
    }

    @Test
    fun dim() {
        val t1 = ArrayTuple(
                Rational(0), Rational(1), Rational(2), Rational(3))
        val t2 = ArrayTuple(
                Rational(0), Rational(2), Rational(4), Rational(6))
        val t3 = ArrayTuple(
                Rational(1), Rational(1), Rational(2), Rational(3))
        val t4 = ArrayTuple(
                Rational(1), Rational(1), Rational(1), Rational(3))
        val t5 = ArrayTuple(
                Rational(1), Rational(1), Rational(1), Rational(1))
        val t6 = ArrayTuple(
                Rational(7), Rational(-1), Rational(3), Rational(19))

        var s = SubSpace.span(t1)
        assertEquals(1, s.dim)
        assertEquals(s.dim, s.basis().size)

        s = SubSpace.span(t1, t2)
        assertEquals(1, s.dim)
        assertEquals(s.dim, s.basis().size)

        s = SubSpace.span(t1, t3)
        assertEquals(2, s.dim)
        assertEquals(s.dim, s.basis().size)

        s = SubSpace.span(t1, t2, t3, t4)
        assertEquals(3, s.dim)
        assertEquals(s.dim, s.basis().size)

        s = SubSpace.span(t1, t2, t3, t4, t5)
        assertEquals(4, s.dim)
        assertEquals(s.dim, s.basis().size)

        s = SubSpace.span(t1, t2, t3, t4, t5, t6)
        assertEquals(4, s.dim)
        assertEquals(s.dim, s.basis().size)
    }

    @Test
    fun equals() {
        val s1 = SubSpace.zeroDim(3)
        val s2 = SubSpace.zeroDim(3)
        val s3 = SubSpace.zeroDim(5)

        assertEquals(s1, s2)
        assertNotEquals(s1, s3)

        val t1 = ArrayTuple(*RationalArrays.base(3, 0))
        val t2 = ArrayTuple(*RationalArrays.base(3, 1))
        val t3 = ArrayTuple(*RationalArrays.base(3, 2))
        val t4 = ArrayTuple(Rational.ONE,
                Rational.ONE, Rational.ONE)

        val s4 = SubSpace.span(t1, t2, t3)
        val s5 = SubSpace.span(t1, t2, t4)
        assertEquals(s4, s5)
        assertEquals(s4.hashCode().toLong(), s5.hashCode().toLong())

        val s6 = SubSpace.span(t1, t2)
        val s7 = SubSpace.span(t1, t4)
        assertNotEquals(s6, s7)
    }

    @Test
    fun hashCodeTest() {
        val s1 = SubSpace.zeroDim(3)
        val s2 = SubSpace.zeroDim(3)

        assertEquals(s1.hashCode().toLong(), s2.hashCode().toLong())
    }
}
