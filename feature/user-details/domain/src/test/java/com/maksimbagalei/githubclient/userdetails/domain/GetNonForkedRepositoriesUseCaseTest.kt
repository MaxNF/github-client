package com.maksimbagalei.githubclient.userdetails.domain

import android.util.Log
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.maksimbagalei.githubclient.common.test.BaseTest
import com.maksimbagalei.githubclient.userdetails.data.dto.Repository
import com.maksimbagalei.githubclient.userdetails.data.paging.UserRepositoriesPagerFactory
import com.maksimbagalei.githubclient.userdetails.repositoryMock
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

class GetNonForkedRepositoriesUseCaseTest : BaseTest() {

    @MockK
    private lateinit var factory: UserRepositoriesPagerFactory

    @MockK
    private lateinit var pager: Pager<Int, Repository>

    private lateinit var useCase: GetNonForkedRepositoriesUseCase

    override fun setUp() {
        super.setUp()
        useCase = GetNonForkedRepositoriesUseCase(factory)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `use case invoked, data from flow is correctly filtered`() = runTest {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        every { Log.isLoggable(any(), any()) } returns false
        every { factory.createPager(any()) }.returns(pager)

        val fakePagingData = PagingData.from(
            listOf(
                repositoryMock.copy(id = 0, isFork = false),
                repositoryMock.copy(id = 1, isFork = true)
            )
        )
        every { pager.flow }.returns(
            flowOf(fakePagingData)
        )
        val data = useCase.invoke("some_login").first()
        val differ = AsyncPagingDataDiffer(
            diffCallback = TestDiffCallback<Repository>(),
            updateCallback = TestListCallback(),
            workerDispatcher = Dispatchers.Main
        )
        differ.submitData(data)
        val result = differ.snapshot()
        assertContentEquals(
            listOf(repositoryMock.copy(id = 0, isFork = false)),
            result
        )
    }
}

private class TestListCallback : ListUpdateCallback {
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
}

private class TestDiffCallback<T> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T & Any, newItem: T & Any): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: T & Any, newItem: T & Any): Boolean {
        return oldItem == newItem
    }
}