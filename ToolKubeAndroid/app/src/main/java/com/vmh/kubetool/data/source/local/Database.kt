package com.vmh.kubetool.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vmh.kubetool.data.models.Search

@Database(entities = [Search::class], version = 1, exportSchema = false)
abstract class Database: RoomDatabase() {
    abstract fun photoDao(): DAO
}
