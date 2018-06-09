package xyz.realmath

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test
import kotlin.test.assertTrue

class AbelianGroupTest {
    @Test
    fun <T : AbelianGroup<T>> isZero() {
        @Suppress("UNCHECKED_CAST")
        val ringElm = mockk<AbelianGroup<T>>() as T
        every { ringElm.zero() } returns ringElm

        assertTrue(ringElm.isZero)
    }

    @Test
    fun <T : AbelianGroup<T>> minus() {
        @Suppress("UNCHECKED_CAST")
        val ringElm = mockk<AbelianGroup<T>>(relaxed = true) as T
        @Suppress("UNCHECKED_CAST")
        val negRingElm = mockk<AbelianGroup<T>>(relaxed = true) as T
        every { ringElm.unaryMinus() } returns negRingElm

        ringElm - ringElm

        verify { ringElm.unaryMinus() }
        verify { ringElm.plus(negRingElm) }
    }
}
