package com.maksimbagalei.githubclient.userdetails.data.di

import com.maksimbagalei.githubclient.userdetails.data.network.UserDetailsGithubApi
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
    fun provideUserListGithubApi(retrofit: Retrofit): UserDetailsGithubApi =
        UserDetailsGithubApi.create(retrofit)
}