package com.vmh.kubetool.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LinkResponse(
    @SerializedName("message")
    var message: String,
    @SerializedName("linkName")
    var linkName: String
): Parcelable
