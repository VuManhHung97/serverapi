package com.vmh.kubetool.data.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName("id")
    @Expose
    var id: Int? = null,
    @SerializedName("fullname")
    @Expose
    var fullname: String? = null,
    @SerializedName("phone_number")
    @Expose
    var phoneNumber: String? = null,
    @SerializedName("username")
    @Expose
    var username: String? = null,
    @SerializedName("email")
    @Expose
    var email: String? = null,
    @SerializedName("code")
    @Expose
    var code: String? = null,
    @SerializedName("status")
    @Expose
    var status: String? = null,
    @SerializedName("create_date")
    @Expose
    var createDate: String? = null,
) : Parcelable
