package com.samseptiano.fortius.base.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.samseptiano.fortius.data.dataStore.UserPreferences
import com.samseptiano.fortius.data.repository.FortiusDataSourceImpl
import com.samseptiano.fortius.data.repository.MapDataSourceImpl
import com.samseptiano.fortius.data.repository.RoomAbsensionDataSourceImpl
import com.samseptiano.fortius.domain.usecase.GetMapLocationUseCase
import com.samseptiano.fortius.domain.usecase.LoginUseCase
import com.samseptiano.fortius.domain.usecase.absensionUseCase.DeleteAbsensionUseCase
import com.samseptiano.fortius.domain.usecase.absensionUseCase.GetAbsensionUseCase
import com.samseptiano.fortius.domain.usecase.absensionUseCase.InsertAbsensionUseCase
import com.samseptiano.fortius.domain.usecase.absensionUseCase.IsAlreadyCheckInUseCase
import com.samseptiano.fortius.ui.viewmodel.HomeViewModel
import com.samseptiano.fortius.ui.viewmodel.LoginViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author SamuelSep on 4/20/2021.
 */
@Singleton
@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(
    private val roomAbsensionRepository: RoomAbsensionDataSourceImpl,
    private val fortiusRepository: FortiusDataSourceImpl,
    private val mapsRepository: MapDataSourceImpl,
    @ApplicationContext private val context: Context
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(
                UserPreferences(context),
                InsertAbsensionUseCase(roomAbsensionRepository),
                DeleteAbsensionUseCase(roomAbsensionRepository),
                IsAlreadyCheckInUseCase(roomAbsensionRepository),
                GetAbsensionUseCase(roomAbsensionRepository),
                GetMapLocationUseCase(mapsRepository)
            ) as T


            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(
                UserPreferences(context),
                LoginUseCase(fortiusRepository)
            ) as T

            else -> throw IllegalArgumentException("Unknown viewModel class $modelClass")
        }
    }

}