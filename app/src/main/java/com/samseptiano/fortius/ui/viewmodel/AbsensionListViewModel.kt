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
class AbsensionListViewModel @Inject constructor(
    private val getAbsensionUseCase: GetAbsensionUseCase

) : BaseViewModel() {

    private val _getAbsensionListData = MutableLiveData<ArrayList<AbsensionModel>>()
    internal val getAbsensionListData: LiveData<ArrayList<AbsensionModel>>
        get() = _getAbsensionListData


    private val _getAbsensionData =
        MutableLiveData<ArrayList<Pair<AbsensionModel, AbsensionModel>>>()
    internal val getAbsensionData: LiveData<ArrayList<Pair<AbsensionModel, AbsensionModel>>>
        get() = _getAbsensionData


    suspend fun getAbsension(id: String?, date: String?) {
        val params = GetAbsensionUseCase.Params(id, date, STATUS_CHECK_IN)
        val params2 = GetAbsensionUseCase.Params(id, date, STATUS_CHECK_OUT)

        val result = getAbsensionUseCase.run(params)
        val result2 = getAbsensionUseCase.run(params2)

        if (result.isNotEmpty()) {
            result.mapIndexed { index, absensionModel ->
                if (result2.isEmpty()) {
                    result2.add(AbsensionModel(0, "", "", "", "", "", "", "", "", "", 0.0, 0.0, ""))
                }
                _getAbsensionData.postValue(arrayListOf(Pair(absensionModel, result2[index])))
            }
        }
        else{
            _getAbsensionData.postValue(arrayListOf())
        }

    }

    suspend fun getAbsensionListbyDate(date: String?) {
        val params = GetAbsensionUseCase.Params(null, date, null)
        val result = getAbsensionUseCase.run(params)
        _getAbsensionListData.postValue(result)
    }
}
