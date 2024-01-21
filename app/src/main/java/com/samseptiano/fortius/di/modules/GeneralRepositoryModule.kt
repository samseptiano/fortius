package com.samseptiano.fortius.di.modules

import com.samseptiano.fortius.data.domain.APIMapService
import com.samseptiano.fortius.data.domain.APIService
import com.samseptiano.fortius.data.repository.FortiusDataSourceImpl
import com.samseptiano.fortius.data.repository.MapDataSourceImpl
import com.samseptiano.fortius.data.repository.RoomAbsensionDataSourceImpl
import com.samseptiano.fortius.data.repository.datasource.FortiusDataSource
import com.samseptiano.fortius.data.repository.datasource.MapDataSource
import com.samseptiano.fortius.data.repository.datasource.RoomAbsensionDataSource
import com.samseptiano.fortius.domain.dao.AbsensionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GeneralRepositoryModule {
    @Singleton
    @Provides
    fun provideRoomsDataSource(absensionDao: AbsensionDao): RoomAbsensionDataSource {
        return RoomAbsensionDataSourceImpl(absensionDao)
    }

    @Singleton
    @Provides
    fun provideFortiusDataSource(apiService: APIService): FortiusDataSource {
        return FortiusDataSourceImpl(apiService)
    }


    @Singleton
    @Provides
    fun provideMapsDataSource(apiService: APIMapService): MapDataSource {
        return MapDataSourceImpl(apiService)
    }
}