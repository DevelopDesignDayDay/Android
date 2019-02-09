package com.ddd.attendance.check.di.module

import android.content.Context
import com.ddd.attendance.check.BuildConfig
import com.ddd.attendance.check.DDDApplication
import com.ddd.attendance.check.api.ApiService
import com.ddd.attendance.check.extension.SharedPreferHelper
import com.ddd.attendance.check.extension.SharedPreferHelper.DATA_PREFER
import com.ddd.attendance.check.extension.get
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.timeunit.TimeUnit
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
class NetworkModule {

    @Provides
    fun provideInterceptor(application: DDDApplication): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val token = application.getSharedPreferences(DATA_PREFER, Context.MODE_PRIVATE).get(SharedPreferHelper.TOKEN_KEY, "")
            val headers = request.headers().newBuilder()
                    .add(HEADER_AUTH_PARAM,
                            if (token.isNotEmpty()) token
                            else Credentials.basic(BuildConfig.BASIC_AUTH_USER_NAME, BuildConfig.BASIC_AUTH_USER_PASSWORD))
                    .build()
            chain.proceed(request.newBuilder().headers(headers).build())
        }
    }

    @Provides
    fun createClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
            if (BuildConfig.DEBUG) addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            connectTimeout(NETWORK_TIME_OUT, TimeUnit.SECONDS)
            readTimeout(NETWORK_TIME_OUT, TimeUnit.SECONDS)
            addInterceptor(interceptor)
        }.build()
    }

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
                .setLenient()
                .create()
    }

    @Provides
    fun provideApiService(okHttpClient: OkHttpClient, gson: Gson): ApiService {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(ApiService::class.java)
    }

    companion object {
        const val HEADER_AUTH_PARAM = "Authorization"
        const val NETWORK_TIME_OUT: Long = 15
    }
}