package xyz.realmath

import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test

class UnitalRingTest {
    @Test
    fun <T : UnitalRing<T>> minusInt() {
        val ringElm = mockk<UnitalRing<T>>(relaxed = true)
        ringElm - 3
        verify { ringElm + -3 }
    }
}
