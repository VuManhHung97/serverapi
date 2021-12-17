package com.vmh.kubetool.di

import android.app.Application
import com.vmh.kubetool.data.source.RepositoryModule
import com.vmh.kubetool.utils.BaseApplication
import com.vmh.kubetool.utils.SessionManager
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityBuilderModule::class,
    AppModule::class,
    ViewModelFactoryModule::class,
    RepositoryModule::class
])
interface ApplicationComponent: AndroidInjector<BaseApplication> {

    fun sessionManager(): SessionManager

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}