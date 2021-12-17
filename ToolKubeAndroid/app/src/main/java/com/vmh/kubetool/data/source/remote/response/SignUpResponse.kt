package com.vmh.kubetool.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SignUpResponse(
    @SerializedName("message")
    var message: String
): Parcelable
