package com.vmh.kubetool.data.source.local

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.vmh.kubetool.utils.Constants
import dagger.Module
import dagger.Provides

@Module
class LocalDataModule {

    @Provides
    fun provideDb(context: Application): Database {
        return Room.databaseBuilder(
            context.applicationContext,
            Database::class.java,
            Constants.PHOTO_ROOM_DB_STRING
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providePhotoDao(db: Database): DAO {
        return db.photoDao()
    }

    @Provides
    fun provideSharedPrefsApi(context: Application, gson: Gson): SharedPrefsApi {
        return SharedPrefsApi(context, gson)
    }

    @Provides
    fun providerGson(): Gson {
        return Gson()
    }
}
