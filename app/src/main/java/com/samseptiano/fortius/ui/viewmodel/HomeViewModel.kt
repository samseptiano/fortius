package com.samseptiano.fortius.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.samseptiano.fortius.data.dataStore.UserPreferences
import com.samseptiano.fortius.data.model.response.AbsensionModel
import com.samseptiano.fortius.domain.usecase.absensionUseCase.DeleteAbsensionUseCase
import com.samseptiano.fortius.domain.usecase.absensionUseCase.GetAbsensionUseCase
import com.samseptiano.fortius.domain.usecase.absensionUseCase.InsertAbsensionUseCase
import com.samseptiano.fortius.domain.usecase.absensionUseCase.IsAlreadyCheckInUseCase
import com.samseptiano.fortius.utils.Constant.Companion.STATUS_CHECK_IN
import com.samseptiano.fortius.utils.Constant.Companion.STATUS_CHECK_OUT
import com.samseptiano.base.viewmodel.BaseViewModel
import com.samseptiano.fortius.data.model.response.MapResponse
import com.samseptiano.fortius.domain.usecase.GetMapLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val insertAbsensionUseCase: InsertAbsensionUseCase,
    private val deleteAbsensionUseCase: DeleteAbsensionUseCase,
    private val isAlreadyCheckInUseCase: IsAlreadyCheckInUseCase,
    private val getAbsensionUseCase: GetAbsensionUseCase,
    private val getMapLocationUseCase: GetMapLocationUseCase

) : BaseViewModel() {

    private val _isLogoutSuccess = MutableLiveData<Boolean>()
    internal val isLogoutSuccess: LiveData<Boolean>
        get() = _isLogoutSuccess

    private val _isAbsensionSuccess = MutableLiveData<Boolean>()
    internal val isAbsensionSuccess: LiveData<Boolean>
        get() = _isAbsensionSuccess

    private val _getAbsensionCheckInData = MutableLiveData<AbsensionModel>()
    internal val getAbsensionCheckInData: LiveData<AbsensionModel>
        get() = _getAbsensionCheckInData

    private val _getAbsensionCheckOutData = MutableLiveData<AbsensionModel>()
    internal val getAbsensionCheckOutData: LiveData<AbsensionModel>
        get() = _getAbsensionCheckOutData

    private val _getLocationCheckInData = MutableLiveData<MapResponse>()
    internal val getLocationCheckInData: LiveData<MapResponse>
        get() = _getLocationCheckInData

    init {
        _isLogoutSuccess.postValue(false)
        _isAbsensionSuccess.postValue(false)
    }

    suspend fun clearPreferences() {
        userPreferences.clearAll()
        deleteAllAbsension()
        _isLogoutSuccess.postValue(true)
    }

    private suspend fun deleteAllAbsension() {
        val params = DeleteAbsensionUseCase.Params(null, null)
        deleteAbsensionUseCase.run(params)
    }

    suspend fun isAlreadyCheckIn(date: String): Boolean {
        val params = IsAlreadyCheckInUseCase.Params(date)
        return isAlreadyCheckInUseCase.run(params)
    }

    suspend fun insertAbsension(listAbsension: List<AbsensionModel>) {
        _isAbsensionSuccess.postValue(false)
        val params = InsertAbsensionUseCase.Params(listAbsension)
        insertAbsensionUseCase.run(params)
        _isAbsensionSuccess.postValue(true)
    }

    suspend fun getAbsension(id: String?, date: String?, status: String?) {
        val params = GetAbsensionUseCase.Params(id, date, status)
        val result = getAbsensionUseCase.run(params)
        when (status) {
            STATUS_CHECK_IN -> {
                result.map {
                    _getAbsensionCheckInData.postValue(it)
                }
            }
            STATUS_CHECK_OUT -> {
                result.map {
                    _getAbsensionCheckOutData.postValue(it)
                }
            }

        }
    }

    suspend fun getLocationCheckIn(lat: String, lon: String) {
        val params = GetMapLocationUseCase.Params(lat, lon)
        val result = getMapLocationUseCase.run(params)
        result.onSuccess {
            _getLocationCheckInData.postValue(it)
        }
    }
}
