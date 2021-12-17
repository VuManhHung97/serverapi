package com.vmh.kubetool.di.auth

import androidx.lifecycle.ViewModel
import com.vmh.kubetool.di.ViewModelKey
import com.vmh.kubetool.screen.auth.signin.SignInViewModel
import com.vmh.kubetool.screen.auth.signup.SignUpViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AuthViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    abstract fun bindSignInViewModel(viewModel: SignInViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel::class)
    abstract fun bindSignUpViewModel(viewModel: SignUpViewModel): ViewModel

}