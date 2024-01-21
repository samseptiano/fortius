package com.samseptiano.fortius.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.samseptiano.fortius.data.model.response.AbsensionModel
import com.samseptiano.fortius.domain.usecase.absensionUseCase.GetAbsensionUseCase
import com.samseptiano.fortius.utils.Constant.Companion.STATUS_CHECK_IN
import com.samseptiano.fortius.utils.Constant.Companion.STATUS_CHECK_OUT
import com.samseptiano.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AbsensionDetailViewModel @Inject constructor(
    private val getAbsensionUseCase: GetAbsensionUseCase

) : BaseViewModel() {

    private val _getAbsensionData = MutableLiveData<Pair<AbsensionModel, AbsensionModel>>()
    internal val getAbsensionData: LiveData<Pair<AbsensionModel, AbsensionModel>>
        get() = _getAbsensionData


    suspend fun getAbsension(id: String?, date: String?) {
        val params = GetAbsensionUseCase.Params(id, date, STATUS_CHECK_IN)
        val params2 = GetAbsensionUseCase.Params(id, date, STATUS_CHECK_OUT)

        val result = getAbsensionUseCase.run(params)
        val result2 = getAbsensionUseCase.run(params2)

        result.mapIndexed { index, absensionModel ->
            _getAbsensionData.postValue(Pair(absensionModel, result2[index]))
        }

    }
}
