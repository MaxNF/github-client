package com.maksimbagalei.githubclient.userlist.domain

import android.util.Log
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.Pager
import androidx.paging.PagingData
import com.maksimbagalei.githubclient.common.test.BaseTest
import com.maksimbagalei.githubclient.common.test.paging.TestDiffCallback
import com.maksimbagalei.githubclient.common.test.paging.TestListCallback
import com.maksimbagalei.githubclient.userlist.data.dto.UserBrief
import com.maksimbagalei.githubclient.userlist.data.paging.UserListPagerFactory
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Test
import kotlin.test.assertContentEquals

class SearchUsersUseCaseTest : BaseTest() {

    @MockK
    private lateinit var factory: UserListPagerFactory

    @MockK
    private lateinit var pager: Pager<Int, UserBrief>

    private lateinit var useCase: SearchUsersUseCase

    override fun setUp() {
        super.setUp()
        useCase = SearchUsersUseCase(factory)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `use case invoked, data from paging source is correct`() = runTest {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        every { Log.isLoggable(any(), any()) } returns false
        every { factory.createPager(any()) }.returns(pager)

        val fakePagingData = PagingData.from(listOf(userBriefMock))
        every { pager.flow }.returns(
            flowOf(fakePagingData)
        )
        val data = useCase.invoke("some_login").first()
        val differ = AsyncPagingDataDiffer(
            diffCallback = TestDiffCallback<UserBrief>(),
            updateCallback = TestListCallback(),
            workerDispatcher = Dispatchers.Main
        )
        differ.submitData(data)
        val result = differ.snapshot()
        assertContentEquals(
            listOf(userBriefMock),
            result
        )
    }
}