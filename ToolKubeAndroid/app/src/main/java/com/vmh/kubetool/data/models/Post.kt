package com.vmh.kubetool.data.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Post(
    @SerializedName("userId")
    @Expose
    val userId: Int,
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("title")
    @Expose
    val title: String,
    @SerializedName("body")
    @Expose
    val body: String
): Parcelable {
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass){
            return false
        }

        other as Post

        if (id != other.id) {
            return false
        }

        if (userId != other.userId) {
            return false
        }

        if (title != other.title) {
            return false
        }

        if (body != other.body) {
            return false
        }

        return true
    }
}
