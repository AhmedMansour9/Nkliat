package com.nkliat.Model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class CreateOrder_Response(
    @SerializedName("data")
    var `data`: String?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("success")
    var success: Boolean?
) : Parcelable