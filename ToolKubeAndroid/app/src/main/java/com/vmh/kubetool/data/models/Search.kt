package com.vmh.kubetool.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "search")
class Search (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var title: String? = null
): Parcelable
