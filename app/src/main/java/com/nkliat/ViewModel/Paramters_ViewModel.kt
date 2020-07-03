package com.nkliat.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nawaqes.Retrofit.ApiClient
import com.nawaqes.Retrofit.Service
import com.nkliat.Model.Paramters_Response
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class Paramters_ViewModel : ViewModel() {

    public var listProductsMutableLiveData: MutableLiveData<Paramters_Response>? = null
    private lateinit var context: Context

    public var listAllsizesMutableLiveData: MutableLiveData<Paramters_Response>? = null

     fun getData(product_id:String,lang:String ,context: Context): LiveData<Paramters_Response> {
        listProductsMutableLiveData = MutableLiveData<Paramters_Response>()
        this.context = context
        getDataValues(product_id,lang)
        return listProductsMutableLiveData as MutableLiveData<Paramters_Response>
    }

    private fun getDataValues(product_id: String,lang:String) {
        var map= HashMap<String,String>()
        map.put("section_id",product_id)
        map.put("lang",lang)

        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.getParamters(map)
        call?.enqueue(object : Callback, retrofit2.Callback<Paramters_Response> {
            override fun onResponse(call: Call<Paramters_Response>, response: Response<Paramters_Response>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else  {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<Paramters_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }



}