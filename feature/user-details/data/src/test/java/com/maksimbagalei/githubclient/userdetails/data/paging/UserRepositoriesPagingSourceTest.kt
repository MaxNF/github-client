package com.maksimbagalei.githubclient.userdetails.data.paging

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.testing.TestPager
import com.maksimbagalei.githubclient.common.test.BaseTest
import com.maksimbagalei.githubclient.data.repo.CallResult
import com.maksimbagalei.githubclient.userdetails.data.repo.UserDetailsRepository
import com.maksimbagalei.githubclient.userdetails.data.repositoryMock
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertNull

private const val LOGIN = "login"

class UserRepositoriesPagingSourceTest : BaseTest() {

    @MockK
    private lateinit var repo: UserDetailsRepository

    private lateinit var pagingSource: UserRepositoriesPagingSource

    override fun setUp() {
        super.setUp()
        pagingSource = UserRepositoriesPagingSource(repo, LOGIN)
    }

    @Test
    fun `pager is refreshed, content is correctly loaded`() {
        val mockResult = List(10) { repositoryMock }
        coEvery { repo.getUserRepos(any(), any(), any()) }.returns(
            CallResult.Success(mockResult)
        )

        runTest {
            val pager = TestPager(PagingConfig(100), pagingSource)
            val result = pager.refresh() as PagingSource.LoadResult.Page
            assertContentEquals(result.data, mockResult)
        }
    }

    @Test
    fun `pager is refreshed and appended two times, content is correctly loaded`() {
        val mockResult = List(10) { repositoryMock }
        coEvery { repo.getUserRepos(any(), any(), any()) }.returns(
            CallResult.Success(mockResult)
        )

        runTest {
            val pager = TestPager(PagingConfig(pageSize = 10, initialLoadSize = 10), pagingSource)
            with(pager) {
                refresh()
                append()
                append()
            }
            val pages = pager.getPages()
            assertEquals(3, pages.size)
            assertContentEquals(List(30) { repositoryMock }, pages.flatten())
        }
    }

    @Test
    fun `pager refreshed, repo returns error, last page is not loaded`() {
        coEvery { repo.getUserRepos(any(), any(), any()) }.returns(
            CallResult.OtherError(Exception())
        )

        runTest {
            val pager = TestPager(PagingConfig(pageSize = 10, initialLoadSize = 10), pagingSource)
            pager.refresh()
            assertNull(pager.getLastLoadedPage())
        }
    }

    @Test
    fun `pager refreshed and appended multiple times, repo returns data then error then data again, page results are correct`() =
        runTest {

            val mockResult = List(10) { repositoryMock.copy(id = 0) }
            coEvery { repo.getUserRepos(any(), any(), any()) }.returns(
                CallResult.Success(mockResult)
            )

            val pager = TestPager(PagingConfig(pageSize = 10, initialLoadSize = 10), pagingSource)
            pager.refresh()

            coEvery { repo.getUserRepos(any(), any(), any()) }.returns(
                CallResult.OtherError(Exception())
            )
            pager.append()

            assertEquals(1, pager.getPages().size)

            val newMock = List(5) { repositoryMock.copy(id = 1) }

            coEvery { repo.getUserRepos(any(), any(), any()) }.returns(
                CallResult.Success(newMock)
            )

            pager.append()

            assertEquals(2, pager.getPages().size)
            assertContentEquals(mockResult, pager.getPages()[0].data)
            assertContentEquals(newMock, pager.getPages()[1].data)
        }
}
