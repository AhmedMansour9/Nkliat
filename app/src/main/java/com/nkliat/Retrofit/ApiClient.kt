package com.nawaqes.Retrofit

import com.google.gson.GsonBuilder
import com.nkliat.Retrofit.MyInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


class ApiClient {
    companion object {
        private val BasuRl: String = "http://nagliyat.com/api/"
        val BASE_URL2 = "https://secure5.tranzila.com/cgi-bin/"
         var retrofi2: Retrofit? = null


        private var retrofit: Retrofit? = null
        public fun getClient(): Retrofit? {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val interceptor=HttpLoggingInterceptor();
            interceptor.level=HttpLoggingInterceptor.Level.BODY
            val inter=MyInterceptor()

            val okHttpClient = OkHttpClient().newBuilder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(inter)
                .build()

            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BasuRl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .client(okHttpClient)
                    .build()
            }

            return retrofit
        }


    }
}