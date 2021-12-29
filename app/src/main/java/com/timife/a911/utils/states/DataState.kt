package com.timife.a911.utils.states

sealed class DataState<out R> {

    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val exception: Exception?, val message: String) : DataState<Nothing>()
    object Loading : DataState<Nothing>()


    override fun toString(): String {
        return when(this){
            is Success<*> -> "Success[data = $data]"
            is Error -> "Error[exception = $exception]"
            is Loading -> "Loading"
        }
    }
}

