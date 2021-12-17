package com.vmh.kubetool.di.main

import androidx.lifecycle.ViewModel
import com.vmh.kubetool.di.ViewModelKey
import com.vmh.kubetool.screen.main.MainViewModel
import com.vmh.kubetool.screen.main.information.InformationViewModel
import com.vmh.kubetool.screen.main.posts.PostsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(InformationViewModel::class)
    abstract fun bindInformationViewModel(viewModel: InformationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PostsViewModel::class)
    abstract fun bindPostsViewModel(viewModel: PostsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun binMainViewModel(viewModel: MainViewModel): ViewModel
}
