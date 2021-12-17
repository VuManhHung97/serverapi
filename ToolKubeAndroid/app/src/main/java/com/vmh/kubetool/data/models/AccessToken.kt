package com.vmh.kubetool.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class AccessToken(
    @SerializedName("token")
    var token: String,
    @SerializedName("refreshToken")
    var refreshToken: String,
    @SerializedName("message")
    var message: String
): Parcelable
