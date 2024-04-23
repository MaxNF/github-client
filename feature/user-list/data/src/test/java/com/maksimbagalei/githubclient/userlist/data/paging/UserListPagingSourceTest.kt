package com.maksimbagalei.githubclient.userlist.data.paging

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.testing.TestPager
import com.maksimbagalei.githubclient.common.test.BaseTest
import com.maksimbagalei.githubclient.data.repo.CallResult
import com.maksimbagalei.githubclient.userlist.data.dto.UserList
import com.maksimbagalei.githubclient.userlist.data.repo.UserListRepository
import com.maksimbagalei.githubclient.userlist.data.userBriefMock
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
    private lateinit var repo: UserListRepository

    private lateinit var pagingSource: UserListPagingSource

    override fun setUp() {
        super.setUp()
        pagingSource = UserListPagingSource(repo, LOGIN)
    }

    @Test
    fun `pager is refreshed, content is correctly loaded`() = runTest {
        val mockResult = UserList(items = List(10) { userBriefMock })
        coEvery { repo.searchUsers(any(), any(), any()) }.returns(
            CallResult.Success(mockResult)
        )

        val pager = TestPager(PagingConfig(100), pagingSource)
        val result = pager.refresh() as PagingSource.LoadResult.Page
        assertContentEquals(result.data, mockResult.items)
    }

    @Test
    fun `pager is refreshed and appended two times, content is correctly loaded`() = runTest {
        val mockResult = UserList(items = List(10) { userBriefMock })
        coEvery { repo.searchUsers(any(), any(), any()) }.returns(
            CallResult.Success(mockResult)
        )

        val pager = TestPager(PagingConfig(pageSize = 10, initialLoadSize = 10), pagingSource)
        with(pager) {
            refresh()
            append()
            append()
        }
        val pages = pager.getPages()
        assertEquals(3, pages.size)
        assertContentEquals(List(30) { userBriefMock }, pages.flatten())
    }

    @Test
    fun `pager refreshed, repo returns error, last page is not loaded`() = runTest {
        coEvery { repo.searchUsers(any(), any(), any()) }.returns(
            CallResult.OtherError(Exception())
        )

        val pager = TestPager(PagingConfig(pageSize = 10, initialLoadSize = 10), pagingSource)
        pager.refresh()
        assertNull(pager.getLastLoadedPage())
    }

    @Test
    fun `pager refreshed and appended multiple times, repo returns data then error then data again, page results are correct`() =
        runTest {

            val mockResult = UserList(items = List(10) { userBriefMock })
            coEvery { repo.searchUsers(any(), any(), any()) }.returns(
                CallResult.Success(mockResult)
            )

            val pager = TestPager(PagingConfig(pageSize = 10, initialLoadSize = 10), pagingSource)
            pager.refresh()

            coEvery { repo.searchUsers(any(), any(), any()) }.returns(
                CallResult.OtherError(Exception())
            )
            pager.append()

            assertEquals(1, pager.getPages().size)

            val newMock = UserList(items = List(10) { userBriefMock })

            coEvery { repo.searchUsers(any(), any(), any()) }.returns(
                CallResult.Success(newMock)
            )

            pager.append()

            assertEquals(2, pager.getPages().size)
            assertContentEquals(mockResult.items, pager.getPages()[0].data)
            assertContentEquals(newMock.items, pager.getPages()[1].data)
        }
}

