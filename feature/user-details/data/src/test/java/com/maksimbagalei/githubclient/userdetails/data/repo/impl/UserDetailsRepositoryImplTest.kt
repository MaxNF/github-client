package com.maksimbagalei.githubclient.userdetails.data.repo.impl

import com.maksimbagalei.githubclient.common.test.BaseTest
import com.maksimbagalei.githubclient.data.repo.CallResult
import com.maksimbagalei.githubclient.userdetails.data.network.UserDetailsGithubApi
import com.maksimbagalei.githubclient.userdetails.data.repo.UserDetailsRepository
import com.maksimbagalei.githubclient.userdetails.data.repositoryMock
import com.maksimbagalei.githubclient.userdetails.data.userDetailsMock
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UserDetailsRepositoryImplTest : BaseTest() {

    @MockK
    private lateinit var networkApi: UserDetailsGithubApi

    private lateinit var repo: UserDetailsRepository

    override fun setUp() {
        super.setUp()
        repo = UserDetailsRepositoryImpl(networkApi)
    }

    @Test
    fun `getUserDetails() finishes without exception, result is Success`() = runTest {
        coEvery { networkApi.getUserDetails(any()) }.returns(userDetailsMock)
        val result = repo.getUserDetails("") as CallResult.Success
        assertEquals(userDetailsMock, result.value)
    }

    @Test
    fun `getUserDetails() throws Exception, result is OtherError`() = runTest {
        coEvery { networkApi.getUserDetails(any()) }.throws(Exception())
        val result = repo.getUserDetails("")
        assertTrue { result is CallResult.OtherError }
    }

    @Test
    fun `getUserDetails() throws HttpException, result is HttpError`() = runTest {
        coEvery { networkApi.getUserDetails(any()) }.throws(
            HttpException(
                Response.error<Nothing>(
                    400,
                    "".toResponseBody()
                )
            )
        )
        val result = repo.getUserDetails("") as CallResult.HttpError
        assertEquals(400, result.code)
    }

    @Test
    fun `getUserRepos() finishes without exception, result is Success`() = runTest {
        val mock = listOf(repositoryMock)
        coEvery { networkApi.getUserRepos(any(), any(), any()) }.returns(mock)
        val result = repo.getUserRepos("", 1, 1) as CallResult.Success
        assertEquals(mock, result.value)
    }

    @Test
    fun `getUserRepos() throws Exception, result is OtherError`() = runTest {
        coEvery { networkApi.getUserRepos(any(), any(), any()) }.throws(Exception())
        val result = repo.getUserRepos("", 1, 1)
        assertTrue { result is CallResult.OtherError }
    }

    @Test
    fun `getUserRepos() throws HttpException, result is HttpError`() = runTest {
        coEvery { networkApi.getUserRepos(any(), any(), any()) }.throws(
            HttpException(
                Response.error<Nothing>(
                    400,
                    "".toResponseBody()
                )
            )
        )
        val result = repo.getUserRepos("", 1, 1) as CallResult.HttpError
        assertEquals(400, result.code)
    }
}