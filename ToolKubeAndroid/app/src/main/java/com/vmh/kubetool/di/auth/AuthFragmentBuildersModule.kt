package com.vmh.kubetool.di.auth

import com.vmh.kubetool.screen.auth.signin.SignInFragment
import com.vmh.kubetool.screen.auth.signup.SignUpFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AuthFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeSignInFragment(): SignInFragment

    @ContributesAndroidInjector
    abstract fun contributeSignUpFragment(): SignUpFragment
}