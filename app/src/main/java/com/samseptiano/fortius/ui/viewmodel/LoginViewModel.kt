package com.samseptiano.fortius.ui.viewmodel


import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.samseptiano.fortius.data.dataStore.UserPreferences
import com.samseptiano.fortius.data.dataStore.UserPreferencesModel
import com.samseptiano.fortius.data.model.request.LoginRequest
import com.samseptiano.fortius.domain.usecase.LoginUseCase
import com.samseptiano.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val loginUseCase: LoginUseCase,
) : BaseViewModel() {
    private val _isLoginSuccess = MutableLiveData<Boolean>()
    internal val isLoginSuccess: LiveData<Boolean>
        get() = _isLoginSuccess

    init {
        _isLoginSuccess.postValue(false)
    }


    suspend fun callLogin(context: Context, username: String, password: String) {
        val param = LoginUseCase.Params(LoginRequest(username, password))


        loginUseCase.run(param).apply {
            onSuccess {
                if (it?.data != null) {
                    val responseLogin = UserPreferencesModel(
                        isLogin = true,
                        id = it.data.user?.id ?: "-",
                        name = it.data.user?.name ?: "-",
                        token = it.data.token ?: "-",
                        email = it.data.user?.email ?: "-",
                        role = it.data.user?.role ?: "-",
                        company_id = it.data.user?.companyId ?: "-",
                        company_name = it.data.user?.company?.companyName ?: "-",
                        employee_id = it.data.user?.employee?.employeeId ?: "-"
                    )
                    viewModelScope.launch {
                        saveToDataStore(responseLogin)
                    }
                    Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show()
                    _isLoginSuccess.postValue(true)
                } else {
                    Toast.makeText(context, "Username or password incorrect", Toast.LENGTH_SHORT)
                        .show()
                    _isLoginSuccess.postValue(false)
                }
            }.onError {
                handleErrorException(it)
                _isLoginSuccess.postValue(false)

            }
        }

    }

     fun validateLogin(username:String, password: String): Boolean {
        return (username.isNotEmpty() && password.isNotEmpty())
    }

    private suspend fun saveToDataStore(
        userPreferencesModel: UserPreferencesModel
    ) {
        userPreferencesModel.saveToDataStore(userPreferences)
    }
}
