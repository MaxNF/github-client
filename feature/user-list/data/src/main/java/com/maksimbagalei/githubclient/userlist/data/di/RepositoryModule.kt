package com.maksimbagalei.githubclient.userlist.data.di

import com.maksimbagalei.githubclient.userlist.data.repo.UserListRepository
import com.maksimbagalei.githubclient.userlist.data.repo.impl.UserListRepositoryImpl
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
    abstract fun bindUserListRepository(repoImpl: UserListRepositoryImpl): UserListRepository
}