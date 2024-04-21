package com.maksimbagalei.githubclient.userdetails.data.di

import com.maksimbagalei.githubclient.userdetails.data.repo.UserDetailsRepository
import com.maksimbagalei.githubclient.userdetails.data.repo.impl.UserDetailsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class RepositoryModule {

    @ViewModelScoped
    @Binds
    abstract fun bindUserDetailsRepository(repoImpl: UserDetailsRepositoryImpl): UserDetailsRepository
}