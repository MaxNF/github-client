package com.maksimbagalei.githubclient.userdetails.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import com.maksimbagalei.githubclient.common.util.createNotLoadingPagingData
import com.maksimbagalei.githubclient.data.repo.CallResult
import com.maksimbagalei.githubclient.userdetails.domain.GetNotForkedRepositoriesUseCase
import com.maksimbagalei.githubclient.userdetails.domain.GetUserDetailsUseCase
import com.maksimbagalei.githubclient.userdetails.ui.mapper.RepositoryToRepositoryModelMapper
import com.maksimbagalei.githubclient.userdetails.ui.mapper.UserDetailsToUserDetailsModelMapper
import com.maksimbagalei.githubclient.userdetails.ui.screenstate.UserDetailsScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val LOGIN_KEY = "login"

@HiltViewModel
internal class UserDetailsViewModel @Inject constructor(
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    private val getNotForkedRepositoriesUseCase: GetNotForkedRepositoriesUseCase,
    private val userDetailsMapper: UserDetailsToUserDetailsModelMapper,
    private val repositoryMapper: RepositoryToRepositoryModelMapper,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _screenState =
        MutableStateFlow<UserDetailsScreenState>(UserDetailsScreenState.Loading)
    val screenState = _screenState.asStateFlow()

    val repositories = savedStateHandle.get<String>(LOGIN_KEY)?.let { login ->
        getNotForkedRepositoriesUseCase.invoke(login).map { it.map(repositoryMapper::map) }
    } ?: flowOf(createNotLoadingPagingData())

    init {
        fetchDetails()
    }

    fun fetchDetails() {
        savedStateHandle.get<String>(LOGIN_KEY)?.let { login ->
            _screenState.value = UserDetailsScreenState.Loading
            viewModelScope.launch {
                when (val userDetails = getUserDetailsUseCase(login)) {
                    is CallResult.Success -> {
                        val model = userDetailsMapper.map(userDetails.value)
                        _screenState.value = UserDetailsScreenState.Loaded(model)
                    }

                    else -> _screenState.value = UserDetailsScreenState.Error
                }
            }
        }
    }
}