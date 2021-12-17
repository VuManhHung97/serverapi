package com.vmh.kubetool.di.main

import com.vmh.kubetool.di.scopes.MainScope
import com.vmh.kubetool.screen.main.posts.PostsAdapter
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @MainScope
    @Provides
    fun provideAdapter(): PostsAdapter {
        return PostsAdapter()
    }

}
