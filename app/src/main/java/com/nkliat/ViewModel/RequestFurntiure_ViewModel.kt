package com.nkliat.ViewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nawaqes.Retrofit.ApiClient
import com.nawaqes.Retrofit.Service
import com.nkliat.Model.CreateOrder_Response
import com.nkliat.Model.FurntiuretransferRequest_Model
import com.nkliat.Model.Paramters_Response
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class RequestFurntiure_ViewModel :ViewModel() {

    public var listProductsMutableLiveData: MutableLiveData<CreateOrder_Response>? = null
    private lateinit var context: Context

    public var listAllsizesMutableLiveData: MutableLiveData<CreateOrder_Response>? = null

    fun getData(Furntiure: FurntiuretransferRequest_Model,token:String ,context: Context): LiveData<CreateOrder_Response> {
        listProductsMutableLiveData = MutableLiveData<CreateOrder_Response>()
        this.context = context
        getDataValues(Furntiure,token)
        return listProductsMutableLiveData as MutableLiveData<CreateOrder_Response>
    }

    private fun getDataValues(Furntiure: FurntiuretransferRequest_Model, token:String) {
        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.CreateorderFurinture(Furntiure,"Bearer "+token)
        call?.enqueue(object : Callback, retrofit2.Callback<CreateOrder_Response> {
            override fun onResponse(call: Call<CreateOrder_Response>, response: Response<CreateOrder_Response>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else  {

                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<CreateOrder_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }


}