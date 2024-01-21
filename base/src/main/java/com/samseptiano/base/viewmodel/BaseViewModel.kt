package com.samseptiano.base.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samseptiano.base.apihandler.exception.BadRequestException
import com.samseptiano.base.apihandler.exception.NotFoundException
import com.samseptiano.base.apihandler.exception.ServerErrorException
import com.samseptiano.base.apihandler.exception.UnauthorizedException
import com.samseptiano.base.coroutine.AppDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel(), CoroutineScope {
    @Inject
    lateinit var appDispatchers: AppDispatchers

    var job = Job()
    private val _loading by lazy { MutableLiveData<Boolean>() }
    val loading: LiveData<Boolean> = _loading
    protected val coroutineScope by lazy { appDispatchers.getScope() }

    private val _errorResponseLiveData = MutableLiveData<Pair<Int, String>>()
    val errorResponseLiveData: LiveData<Pair<Int, String>>
        get() = _errorResponseLiveData

    open fun handleErrorException(exception: Exception){
        when(exception){
            is UnauthorizedException ->{
                _errorResponseLiveData.postValue(Pair(401, "not authorized :$exception"))
            }
            is ServerErrorException ->{
                _errorResponseLiveData.postValue(Pair(500, "internal server error :$exception"))
            }
            is BadRequestException ->{
                _errorResponseLiveData.postValue(Pair(400, "bad request :$exception"))
            }
            is NotFoundException ->{
                _errorResponseLiveData.postValue(Pair(404, "error not found :$exception"))
            }
            else -> {
                _errorResponseLiveData.postValue(Pair(501, "unknown error occurred :$exception"))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
       // coroutineScope.cancel()
        //job.cancel()
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job
}