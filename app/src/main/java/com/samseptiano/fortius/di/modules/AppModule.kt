package com.samseptiano.fortius.di.modules


import android.content.Context
import androidx.room.Room
import com.samseptiano.fortius.data.dataStore.UserPreferences
import com.samseptiano.fortius.domain.dao.AbsensionDao
import com.samseptiano.fortius.domain.database.CrudDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Provides
    @Singleton
    fun providesCrudDatabase(@ApplicationContext context: Context): CrudDatabase =
        Room.databaseBuilder(context, CrudDatabase::class.java, "crud")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun providesAbsensionDao(crudDatabase: CrudDatabase): AbsensionDao =
        crudDatabase.getAbsensiDao()

    @Provides
    @Singleton
    fun providesUserPreferences(@ApplicationContext context: Context): UserPreferences =
        UserPreferences(context)

}