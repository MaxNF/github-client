package com.maksimbagalei.githubclient.userlist.data.di

import com.maksimbagalei.githubclient.userlist.data.network.UserListGithubApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
internal class NetworkModule {

    @ViewModelScoped
    @Provides
    fun provideUserListGithubApi(retrofit: Retrofit): UserListGithubApi =
        UserListGithubApi.create(retrofit)
}