package com.nkliat.Model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Profile_Response(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(

        @SerializedName("email")
        val email: String?,
        @SerializedName("full_name")
        val fullName: String,

        @SerializedName("password")
        val password: String,
        @SerializedName("phone")
        val phone: String,

        @SerializedName("image")
        val image_path:String
    ) : Parcelable
}