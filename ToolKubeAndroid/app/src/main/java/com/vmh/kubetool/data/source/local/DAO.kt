package com.vmh.kubetool.data.source.local

import androidx.room.*
import com.vmh.kubetool.data.models.Search
import io.reactivex.Flowable

@Dao
interface DAO {
    @Query("SELECT * FROM search")
    fun getAllHistory(): Flowable<List<Search>>

    @Delete
    fun deleteHistory(search: Search)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistory(search: Search)
}
