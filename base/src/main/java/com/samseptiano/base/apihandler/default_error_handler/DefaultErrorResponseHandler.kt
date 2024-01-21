package com.samseptiano.base.apihandler.default_error_handler

import com.google.gson.reflect.TypeToken
import com.samseptiano.base.apihandler.ErrorResponseHandler
import com.samseptiano.base.apihandler.exception.BadRequestException
import okhttp3.ResponseBody

class DefaultErrorResponseHandler: ErrorResponseHandler() {
    override fun handleBadRequestError(errorBody: ResponseBody): BadRequestException {
        val type = object: TypeToken<ErrorResponse>(){}.type
        val errorWrapper = errorBody.errorBodyParser<ErrorResponse>(type)
        val errorMessage = errorWrapper?.message.orEmpty()
        return BadRequestException(errorMessage)
    }
}