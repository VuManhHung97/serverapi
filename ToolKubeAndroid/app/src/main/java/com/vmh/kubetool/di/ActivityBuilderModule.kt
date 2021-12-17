package com.vmh.kubetool.di

import com.vmh.kubetool.di.auth.AuthFragmentBuildersModule
import com.vmh.kubetool.di.auth.AuthViewModelModule
import com.vmh.kubetool.di.main.MainFragmentBuildersModule
import com.vmh.kubetool.di.main.MainModule
import com.vmh.kubetool.di.main.MainViewModelModule
import com.vmh.kubetool.di.scopes.AuthScope
import com.vmh.kubetool.di.scopes.MainScope
import com.vmh.kubetool.screen.auth.AuthActivity
import com.vmh.kubetool.screen.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @AuthScope
    @ContributesAndroidInjector(
        modules = [AuthViewModelModule::class, AuthFragmentBuildersModule::class]
    )
    abstract fun contributeAuthActivity(): AuthActivity

    @MainScope
    @ContributesAndroidInjector(
        modules = [MainViewModelModule::class, MainFragmentBuildersModule::class, MainModule::class]
    )
    abstract fun contributeMainActivity(): MainActivity

}
