package com.vmh.kubetool.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.vmh.kubetool.data.models.User
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SignInResponse(
    @SerializedName("message")
    var message: String,
    @SerializedName("user")
    var user: User? = null
): Parcelable