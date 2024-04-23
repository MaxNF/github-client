package com.maksimbagalei.githubclient.common.test

import android.util.Log
import io.mockk.*
import org.junit.After
import org.junit.Before

abstract class BaseTest {

    @Before
    open fun setUp() {
        mockkStatic(Log::class)
        every { Log.v(any(), any()) } returns 0
        every { Log.d(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
        every { Log.e(any(), any(), any()) } returns 0
        MockKAnnotations.init(this)
    }

    @After
    open fun tearDown() {
        unmockkAll()
    }
}