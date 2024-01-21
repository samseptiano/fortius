package com.samseptiano.fortius.domain.usecase

import com.samseptiano.fortius.data.model.request.LoginRequest
import com.samseptiano.fortius.data.model.response.LoginDataResponse
import com.samseptiano.fortius.data.repository.datasource.FortiusDataSource
import com.samseptiano.base.data.ResultState
import com.samseptiano.base.usecase.BaseUseCase
import javax.inject.Inject

/**
 * @author SamuelSep on 4/20/2021.
 */
class LoginUseCase @Inject constructor(
    private val repository: FortiusDataSource) :
    BaseUseCase<ResultState<LoginDataResponse?>, LoginUseCase.Params>() {

    class Params (val loginRequest: LoginRequest)

    override suspend fun run(params: Params): ResultState<LoginDataResponse?> {
        return repository.login(params.loginRequest)
    }

}