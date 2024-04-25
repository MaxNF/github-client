package com.maksimbagalei.githubclient.userdetails.ui.viewmodel

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.maksimbagalei.githubclient.common.util.createNotLoadingPagingData
import com.maksimbagalei.githubclient.data.repo.CallResult
import com.maksimbagalei.githubclient.userdetails.domain.GetNonForkedRepositoriesUseCase
import com.maksimbagalei.githubclient.userdetails.domain.GetUserDetailsUseCase
import com.maksimbagalei.githubclient.userdetails.ui.mapper.RepositoryToRepositoryModelMapper
import com.maksimbagalei.githubclient.userdetails.ui.mapper.UserDetailsToUserDetailsModelMapper
import com.maksimbagalei.githubclient.userdetails.ui.screen.state.UserDetailsScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val LOGIN_KEY = "login"

@HiltViewModel
internal class UserDetailsViewModel @Inject constructor(
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    getNonForkedRepositoriesUseCase: GetNonForkedRepositoriesUseCase,
    private val userDetailsMapper: UserDetailsToUserDetailsModelMapper,
    private val repositoryMapper: RepositoryToRepositoryModelMapper,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val refreshCommandFlow = MutableStateFlow(Any())
    private val repositoriesFlow =
        getNonForkedRepositoriesUseCase.invoke(savedStateHandle.get<String>(LOGIN_KEY)!!)
            .map { it.map(repositoryMapper::map) }.cachedIn(viewModelScope)
            .stateIn(viewModelScope, SharingStarted.Eagerly, createNotLoadingPagingData())


    val screenState = refreshCommandFlow.flatMapLatest {
        flow {
            val login = savedStateHandle.get<String>(LOGIN_KEY)!!
            emit(getUserDetailsUseCase.invoke(login))
        }.combine(repositoriesFlow) { userDetails, pagingData ->
            if (userDetails is CallResult.Success) {
                val userDetailsModel = userDetailsMapper.map(userDetails.value)
                val pagingDataFlow = flowOf(pagingData)
                UserDetailsScreenState.Loaded(userDetailsModel, pagingDataFlow)
            } else {
                UserDetailsScreenState.Error
            }
        }
    }.onStart {
        UserDetailsScreenState.Loading
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserDetailsScreenState.Loading)

    fun reload() {
        savedStateHandle.get<String>(LOGIN_KEY)?.let { login ->
            viewModelScope.launch {
                refreshCommandFlow.value = Any()
            }
        }
    }

    fun openRepo(context: Context, url: String) {
        val intent: CustomTabsIntent = CustomTabsIntent.Builder()
            .build()
        intent.launchUrl(context, Uri.parse(url))
    }
}