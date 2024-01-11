package com.example.pokemonclasses.data.service

import androidx.annotation.ColorRes
import com.example.pokemonclasses.R
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> ResultWrapper.Error(ErrorCodes.NETWORK_ERROR)
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    ResultWrapper.Error(ErrorCodes.fromInt(code), errorResponse)
                }

                else -> {
                    ResultWrapper.Error(null, null)
                }
            }
        }
    }
}

private fun convertErrorBody(throwable: HttpException): String? {
    return try {
        throwable.response()?.errorBody()?.source()?.let {
            val moshiAdapter = Moshi.Builder().build().adapter(String::class.java)
            moshiAdapter.fromJson(it)
        }
    } catch (exception: Exception) {
        null
    }
}

enum class ErrorCodes(val code: Int, val message: String) {
    NETWORK_ERROR(0, "Network exception to call the api"),
    GENERIC_ERROR(400, "Error generic");

    companion object {
        fun fromInt(value: Int) = ErrorCodes.values().firstOrNull { value == it.code } ?: GENERIC_ERROR
    }
}

enum class HeaderType(val title: String, @ColorRes val color: Int) {
    MY_CHAMPS("Mis personajes", com.bumptech.glide.R.color.material_blue_grey_800),
    LOCKED("Bloqueados", R.color.black),
    LEGENDARY("Legendarios", R.color.green_app),
}