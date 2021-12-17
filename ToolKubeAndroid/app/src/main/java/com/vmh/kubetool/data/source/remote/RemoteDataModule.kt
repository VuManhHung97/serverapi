package com.vmh.kubetool.data.source.remote

import com.vmh.kubetool.BuildConfig
import com.vmh.kubetool.data.source.local.SharedPrefsApi
import com.vmh.kubetool.data.source.middeware.TokenAuthenticator
import com.vmh.kubetool.data.source.middeware.TokenInterceptor
import com.vmh.kubetool.network.MainApi
import com.vmh.kubetool.network.TokenService
import com.vmh.kubetool.utils.Constants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class RemoteDataModule {

    @Provides
    fun providerRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun providerMainApi(retrofit: Retrofit, apiServiceHolder: TokenService): MainApi {
        val apiService = retrofit.create(MainApi::class.java)
        apiServiceHolder.setApiService(apiService)
        return apiService
    }

    @Provides
    fun providerTokenAuthenticate(
        tokenService: TokenService,
        sharedPrefsApi: SharedPrefsApi
    ): TokenAuthenticator {
        return TokenAuthenticator(tokenService, sharedPrefsApi)
    }

    @Provides
    fun providerTokenInterceptor(sharedPrefsApi: SharedPrefsApi): TokenInterceptor {
        return TokenInterceptor(sharedPrefsApi)
    }

    @Provides
    fun providerHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
        if (BuildConfig.DEBUG) {
            okHttpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
        }
        return okHttpClient.build()
    }

    @Provides
    fun apiServiceHolder(): TokenService {
        return TokenService()
    }
}
