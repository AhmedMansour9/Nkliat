package com.nkliat.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback
import android.widget.Toast
import android.R.string
import android.os.Build
import androidx.annotation.RequiresApi
import com.nawaqes.Retrofit.ApiClient
import com.nawaqes.Retrofit.Service
import com.nkliat.Model.Register_Model
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import org.json.JSONArray
import java.io.File


class Register_ViewModel : ViewModel() {
    private  var requestImage2:MultipartBody.Part?=null
    public var listProductsMutableLiveData: MutableLiveData<Register_Model>? = null
    private lateinit var context: Context
    private var Wron_Email: Boolean = false

    companion object {

      var LastPhone: String? = ""
        var LastEmail: String? = ""
    }
    fun getStatus():Boolean{
        return Wron_Email
    }

     fun getData(
         filename: File,
        full_name:String,
        Email: String,
        Password:String,
        Phone:String,
        context: Context
    ): LiveData<Register_Model> {
        listProductsMutableLiveData = MutableLiveData<Register_Model>()
        this.context = context
        getDataValues(filename,full_name,Email,Password,Phone)
        return listProductsMutableLiveData as MutableLiveData<Register_Model>
    }
    public fun getLogin(
        Email: String,
        Password:String,
        context: Context
    ): LiveData<Register_Model> {
        listProductsMutableLiveData = MutableLiveData<Register_Model>()
        this.context = context
        getLoginValues(Email,Password)
        return listProductsMutableLiveData as MutableLiveData<Register_Model>

    }
    public fun getLoginFacebook(
        id: String?,
        email:String?,
        name:String?,
        context: Context
    ): LiveData<Register_Model> {
        listProductsMutableLiveData = MutableLiveData<Register_Model>()
        this.context = context
        getface(id,email,name)
        return listProductsMutableLiveData as MutableLiveData<Register_Model>

    }



    private fun getDataValues(filename: File,full_name:String,email: String,Password:String,Phone:String) {
        val full_name= RequestBody.create("multipart/form-data".toMediaTypeOrNull(),full_name)
        val email= RequestBody.create("multipart/form-data".toMediaTypeOrNull(),email)
        val Password= RequestBody.create("multipart/form-data".toMediaTypeOrNull(),Password)
        val Phone= RequestBody.create("multipart/form-data".toMediaTypeOrNull(),Phone)
        val create= RequestBody.create("multipart/form-data".toMediaTypeOrNull(),"create")

        var map= HashMap<String,RequestBody>()
        map.put("full_name",full_name)
        map.put("email",email)
        map.put("password",Password)
        map.put("phone",Phone)
        map.put("password_confirmation",Password)
        map.put("requestType",create)

        val requestFile2 = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), filename)
        requestImage2 =
            MultipartBody.Part.createFormData("image", filename?.name, requestFile2)

        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.userRegister(requestImage2,map)
        call?.enqueue(object : Callback, retrofit2.Callback<Register_Model> {
            override fun onResponse(call: Call<Register_Model>, response: Response<Register_Model>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else  if(response.code()==401){
                    Wron_Email=true
                        var jObjError = JSONObject(response.errorBody()!!.string())
                        var jOsonData = jObjError.getJSONObject("data")
                    if(let { jOsonData.has("phone")}){
                        LastPhone=jOsonData.getJSONArray("phone").get(0).toString()
                    }
                    if(let { jOsonData.has("email")}){
                        LastEmail=jOsonData.getJSONArray("email").get(0).toString()
                    }
                         listProductsMutableLiveData?.setValue(null)

                }
            }

            override fun onFailure(call: Call<Register_Model>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }
    private fun getLoginValues(Email: String,Password:String) {
        var map= HashMap<String,String>()
        map.put("email",Email)
        map.put("password",Password)


        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.userLogin(map)


        call?.enqueue(object : Callback, retrofit2.Callback<Register_Model> {
            override fun onResponse(call: Call<Register_Model>, response: Response<Register_Model>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else {
                    Wron_Email=true
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<Register_Model>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }
    private fun getface(social_id: String?,email:String?,full_name:String?) {
        var map= HashMap<String,String>()
        map.put("social_id",social_id!!)
        if(email!=null) {
            map.put("email", email)
        }else {
            map.put("email", "")

        }
        map.put("full_name",full_name!!)


        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.userLoginFacebook(map)


        call?.enqueue(object : Callback, retrofit2.Callback<Register_Model> {
            override fun onResponse(call: Call<Register_Model>, response: Response<Register_Model>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else {
                    Wron_Email=true
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<Register_Model>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }
}