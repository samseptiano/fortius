package com.samseptiano.fortius.di.modules

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.samseptiano.fortius.BuildConfig
import com.samseptiano.fortius.BuildConfig.BASE_URL_FORTIUS
import com.samseptiano.fortius.BuildConfig.BASE_URL_MAP
import com.samseptiano.fortius.data.dataStore.UserPreferences
import com.samseptiano.fortius.data.domain.APIService
import com.samseptiano.fortius.di.qualifier.AbsensionOkHttpClientQualifier
import com.samseptiano.fortius.utils.RetrofitUtil
import com.samseptiano.base.coroutine.AppDispatchers
import com.samseptiano.base.coroutine.AppDispatchersImpl
import com.samseptiano.fortius.data.domain.APIMapService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Interceptor.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import javax.net.ssl.X509TrustManager


/**
 * @author SamuelSep on 4/20/2021.
 */

@Module
@InstallIn(SingletonComponent::class)
class NetworkApiModule {

    @Singleton
    @Provides
    fun provideAPIService(
        @AbsensionOkHttpClientQualifier
        okHttpClient: OkHttpClient,
        coroutineCallAdapterFactory: CoroutineCallAdapterFactory,
        gsonConverterFactory: GsonConverterFactory
    ): APIService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_FORTIUS)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(coroutineCallAdapterFactory)
            .build()
            .create(APIService::class.java)
    }

    @Singleton
    @Provides
    fun provideAPIMapsService(
        @AbsensionOkHttpClientQualifier
        okHttpClient: OkHttpClient,
        coroutineCallAdapterFactory: CoroutineCallAdapterFactory,
        gsonConverterFactory: GsonConverterFactory
    ): APIMapService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_MAP)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(coroutineCallAdapterFactory)
            .build()
            .create(APIMapService::class.java)
    }

    @Singleton
    @Provides
    @AbsensionOkHttpClientQualifier
    fun buildOkHttpClient(
        @ApplicationContext context: Context,
        userPreferences: UserPreferences
    ): OkHttpClient {

        val okHttp = OkHttpClient().newBuilder().also { item ->
            val log = HttpLoggingInterceptor()
            log.level = HttpLoggingInterceptor.Level.BODY
            item.addInterceptor(log)
            item.retryOnConnectionFailure(true)
        }

        okHttp.sslSocketFactory(RetrofitUtil.getSslSocketFactory(), RetrofitUtil.getTrustAllCerts()[0] as X509TrustManager)
        okHttp.hostnameVerifier { _, _ -> true };

        CoroutineScope(Dispatchers.IO).launch {
            val token = userPreferences.token.firstOrNull()

            okHttp.addInterceptor(
                Interceptor { chain ->
                    val newRequest: Request = if (!token.isNullOrEmpty()) {
                        chain.request().newBuilder()
                            .addHeader("Authorization", "Bearer $token")
                            .build()
                    } else {
                        chain.request().newBuilder().build()
                    }

                    chain.proceed(newRequest)

                }
            )
        }

        // Add Chucker interceptor only for debug builds
        if (BuildConfig.DEBUG) {
            okHttp.addInterceptor(
                ChuckerInterceptor.Builder(context = context)
                    .collector(ChuckerCollector(context))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(false)
                    .build()
            )
        }

        return okHttp.build()
    }

    @Provides
    @Singleton
    fun provideAppDispatchers(): AppDispatchers {
        return AppDispatchersImpl()
    }

    @Provides
    @Singleton
    fun getGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun getCoroutineCallAdapter(): CoroutineCallAdapterFactory {
        return CoroutineCallAdapterFactory.invoke()
    }

}