package com.maksimbagalei.githubclient.userdetails.domain

import com.maksimbagalei.githubclient.userdetails.data.repo.UserDetailsRepository
import javax.inject.Inject

class GetUserDetailsUseCase @Inject constructor(private val repo: UserDetailsRepository) {

    suspend fun invoke(login: String) = repo.getUserDetails(login)
}