package com.vmh.kubetool.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserSave(
    var username: String,
    var password: String
): Parcelable
