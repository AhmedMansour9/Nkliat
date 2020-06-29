package com.nawaqes.Retrofit

import com.nkliat.Model.Register_Model
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface Service {

    @Multipart
    @POST("customer/signup")
     fun userRegister(
        @Part img: MultipartBody.Part?,
        @PartMap map:HashMap<String,@JvmSuppressWildcards RequestBody>): Call<Register_Model>
//
    @POST("customer/login")
    fun userLogin(
        @QueryMap map:Map<String,String>): Call<Register_Model>

    @POST("customer/social_login")
    fun userLoginFacebook(
        @QueryMap map:Map<String,String>): Call<Register_Model>




}