package com.samseptiano.base.usecase

import com.samseptiano.base.coroutine.AppDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

/**
* Created by samuel.septiano on 06/06/2023.
*/

abstract class BaseFlowUseCase<out Type, in Params> where Type : Any {

    abstract fun run(params: Params): Flow<Type>

    operator fun invoke(
        params: Params,
        scope: CoroutineScope,
        appDispatchers: AppDispatchers,
        onResult: (Type) -> Unit = {}
    ) {
        scope.launch {
            run(params)
                .flowOn(appDispatchers.io)
                .collect { result ->
                    onResult(result)
                }
        }
    }

}