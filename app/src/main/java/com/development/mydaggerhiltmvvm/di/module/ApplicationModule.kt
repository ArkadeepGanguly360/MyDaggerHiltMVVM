package com.development.mydaggerhiltmvvm.di.module

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.development.mydaggerhiltmvvm.BuildConfig
import com.development.mydaggerhiltmvvm.restService.AllWebServiceCall
import com.development.mydaggerhiltmvvm.restService.RestInterface
import com.development.mydaggerhiltmvvm.util.MyConstant
import com.development.mydaggerhiltmvvm.util.UserSharedPrefrence
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Cache
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(ApplicationComponent::class)
class ApplicationModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

      @Provides
      @Singleton
      fun getContext(application: Application): Context {
          return application.applicationContext
      }

      @Provides
      @Singleton
      fun getUserSharedPref(context: Context): UserSharedPrefrence {
          return UserSharedPrefrence.getInstance(context)
      }

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .build()


    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        BASE_URL: String
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): RestInterface = retrofit.create(RestInterface::class.java)

    @Provides
    @Singleton
    fun getAllWebServiceCall(): AllWebServiceCall {
        return AllWebServiceCall()
    }
}