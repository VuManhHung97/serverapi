package com.vmh.kubetool.data.source

import com.vmh.kubetool.data.source.local.DAO
import com.vmh.kubetool.data.source.local.LocalDataModule
import com.vmh.kubetool.data.source.local.LocalDataSource
import com.vmh.kubetool.data.source.local.SharedPrefsApi
import com.vmh.kubetool.data.source.remote.RemoteDataModule
import com.vmh.kubetool.data.source.remote.RemoteDataSource
import com.vmh.kubetool.network.MainApi
import dagger.Module
import dagger.Provides

@Module(includes = [LocalDataModule::class, RemoteDataModule::class])
class RepositoryModule {

    @Provides
    fun providerBarberLocalDataSource(
        DAO: DAO,
        sharedPrefsApi: SharedPrefsApi
    ): LocalDataSource {
        return LocalDataSource(sharedPrefsApi, DAO)
    }

    @Provides
    fun providerBarberRemoteDataSource(mainApi: MainApi): RemoteDataSource {
        return RemoteDataSource(mainApi)
    }
}
