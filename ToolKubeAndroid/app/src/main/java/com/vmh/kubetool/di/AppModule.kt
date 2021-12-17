package com.vmh.kubetool.di

import android.app.Application
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.vmh.kubetool.R
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideRequestOption(): RequestOptions {
        return RequestOptions
            .placeholderOf(R.drawable.ic_launcher_background)
            .error(R.drawable.white_background)
    }

    @Singleton
    @Provides
    fun provideGlideInstance(
        application: Application,
        requestOptions: RequestOptions
    ): RequestManager {
        return Glide.with(application)
            .setDefaultRequestOptions(requestOptions)

    }

    @Singleton
    @Provides
    fun provideAppDrawable(application: Application): Drawable? {
        return ContextCompat.getDrawable(application, R.drawable.logo)
    }
}
