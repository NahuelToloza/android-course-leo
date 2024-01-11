package com.example.pokemonclasses.data.service

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Error(
        val errorCodeType: ErrorCodes? = null,
        val remoteErrorMessage: String? = null
    ) : ResultWrapper<Nothing>()
}