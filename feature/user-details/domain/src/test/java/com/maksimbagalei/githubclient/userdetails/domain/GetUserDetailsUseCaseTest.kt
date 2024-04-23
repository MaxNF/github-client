package com.maksimbagalei.githubclient.userdetails.domain

import com.maksimbagalei.githubclient.common.test.BaseTest
import com.maksimbagalei.githubclient.data.repo.CallResult
import com.maksimbagalei.githubclient.userdetails.data.repo.UserDetailsRepository
import com.maksimbagalei.githubclient.userdetails.userDetailsMock
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class GetUserDetailsUseCaseTest : BaseTest() {

    @MockK
    private lateinit var repository: UserDetailsRepository

    private lateinit var useCase: GetUserDetailsUseCase

    override fun setUp() {
        super.setUp()
        useCase = GetUserDetailsUseCase(repository)
    }

    @Test
    fun `repo returns success, use case also returns success`() = runTest {
        val callResult = CallResult.Success(userDetailsMock)
        coEvery { repository.getUserDetails(any()) }.returns(callResult)
        val result = useCase.invoke("some_login")
        assertEquals(callResult, result)
    }

    @Test
    fun `repo returns HttpError, use case also returns HttpError`() = runTest {
        val callResult = CallResult.HttpError(400, Exception())
        coEvery { repository.getUserDetails(any()) }.returns(callResult)
        val result = useCase.invoke("some_login")
        assertEquals(callResult, result)
    }

    @Test
    fun `repo returns OtherError, use case also returns OtherError`() = runTest {
        val callResult = CallResult.OtherError(Exception())
        coEvery { repository.getUserDetails(any()) }.returns(callResult)
        val result = useCase.invoke("some_login")
        assertEquals(callResult, result)
    }
}