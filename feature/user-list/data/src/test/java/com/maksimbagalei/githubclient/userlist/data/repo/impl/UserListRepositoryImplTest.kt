package com.maksimbagalei.githubclient.userlist.data.repo.impl

import com.maksimbagalei.githubclient.common.test.BaseTest
import com.maksimbagalei.githubclient.data.repo.CallResult
import com.maksimbagalei.githubclient.userlist.data.network.UserListGithubApi
import com.maksimbagalei.githubclient.userlist.data.repo.UserListRepository
import com.maksimbagalei.githubclient.userlist.data.userListMock
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UserListRepositoryImplTest : BaseTest() {

    @MockK
    private lateinit var networkApi: UserListGithubApi

    private lateinit var repo: UserListRepository

    override fun setUp() {
        super.setUp()
        repo = UserListRepositoryImpl(networkApi)
    }

    @Test
    fun `searchUsers() finishes without exception, result is Success`() = runTest {
        coEvery { networkApi.searchUsers(any(), any(), any(), any()) }.returns(userListMock)
        val result = repo.searchUsers("", 1, 1) as CallResult.Success
        assertEquals(userListMock, result.value)
    }

    @Test
    fun `searchUsers() throws Exception, result is OtherError`() = runTest {
        coEvery { networkApi.searchUsers(any(), any(), any(), any()) }.throws(Exception())
        val result =  repo.searchUsers("", 1, 1)
        assertTrue { result is CallResult.OtherError }
    }

    @Test
    fun `searchUsers() throws HttpException, result is HttpError`() = runTest {
        coEvery { networkApi.searchUsers(any(), any(), any(), any()) }.throws(
            HttpException(
                Response.error<Nothing>(
                    400,
                    "".toResponseBody()
                )
            )
        )
        val result =  repo.searchUsers("", 1, 1) as CallResult.HttpError
        assertEquals(400, result.code)
    }
}