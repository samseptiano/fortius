package com.samseptiano.base.apihandler

import android.content.res.Resources
import com.samseptiano.base.apihandler.default_error_handler.DefaultErrorResponseHandler
import com.samseptiano.base.apihandler.exception.ServerErrorException
import com.samseptiano.base.apihandler.exception.UnauthorizedException
import retrofit2.Response

object ApiHandler {

    suspend fun <T: Any> handleApi(
        errorHandler: ErrorResponseHandler = DefaultErrorResponseHandler(),
        block: suspend () -> Response<T>
    ): T? {
        try {
            val response = block.invoke()

            if (response.isSuccessful) {
                return response.body()
            }


            // When Error
            throw errorHandler.handle(response.errorBody(), response.code())
        } catch (e: Exception) {
            return when (e) {
                is Resources.NotFoundException -> null
                is UnauthorizedException -> throw UnauthorizedException()
                is ServerErrorException -> throw ServerErrorException()
                else -> throw e
            }
        }
    }
}