package com.vmh.kubetool.di.main

import com.vmh.kubetool.screen.main.information.InformationFragment
import com.vmh.kubetool.screen.main.posts.PostsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeInformationFragment(): InformationFragment

    @ContributesAndroidInjector
    abstract fun contributePostsFragment(): PostsFragment

}
