package com.maksimbagalei.githubclient.userlist.data

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

sealed class CallResult<out T> {
    data class Success<out T>(val value: T) : CallResult<T>()
    data class HttpError(val code: Int? = null, val throwable: Throwable) : CallResult<Nothing>()
    data class OtherError(val throwable: Throwable) : CallResult<Nothing>()
}

open class BaseRepo {
    companion object {
        private const val TAG = "BaseRepo"
    }

    suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher = Dispatchers.IO, apiCall: suspend () -> T): CallResult<T> {
        return withContext(dispatcher) {
            try {
                CallResult.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                Log.e(TAG, "Error while loading data", throwable)
                when (throwable) {
                    is HttpException -> CallResult.HttpError(throwable.code(), throwable)
                    else -> CallResult.OtherError(throwable)
                }
            }
        }
    }
}