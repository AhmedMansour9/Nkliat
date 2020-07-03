package com.nawaqes.Retrofit

import com.nkliat.Model.*
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

    @POST("parameters")
    fun getParamters(
        @QueryMap map:Map<String,String>): Call<Paramters_Response>

    @POST("customer/makeOrder")
    fun CreateorderFurinture(
        @Body Furntiure: FurntiuretransferRequest_Model,@Header("Authorization")auth:String
    ): Call<CreateOrder_Response>


    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET("customer/userInfo")
    fun getProfile(@Header("Authorization")auth:String,@Header("X-localization")lang:String): Call<Profile_Response>


}