package com.maksimbagalei.githubclient.common.util

import com.maksimbagalei.githubclient.common.test.BaseTest
import org.junit.Test
import kotlin.test.assertEquals

class AppBarUtilKtTest : BaseTest() {

    @Test
    fun `test calculateAppBarAlpha`() {
        val a = calculateAppBarAlpha(1f, 1f, 1f)
        assertEquals(1f, a)

        val b = calculateAppBarAlpha(1f, 0.5f, 1f)
        assertEquals(0.5f, b)

        val c = calculateAppBarAlpha(1f, 0.5f, 0.5f)
        assertEquals(0.75f, c)

        val d = calculateAppBarAlpha(0.5f, 1f, 0.5f)
        assertEquals(0.75f, d)

        val e = calculateAppBarAlpha(0f, 1f, 0f)
        assertEquals(0f, e)

        val f = calculateAppBarAlpha(0f, 0.5f, 1f)
        assertEquals(0.5f, f)
    }
}