package com.byandev.submission3uiuxapi.api

import com.byandev.submission3uiuxapi.utils.Constants.Companion.BASE_URL
import com.byandev.submission3uiuxapi.utils.Constants.Companion.TOKEN
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {
        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .addNetworkInterceptor { chain ->
                    val tokenKeys = TOKEN
                    val request = chain.request()
                        .newBuilder()
                        .addHeader("Authorization", "token $tokenKeys")
                        .build()
                    chain.proceed(request)
                }
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
        }
        val api: ApiEndpoint by lazy {
            retrofit.create(ApiEndpoint::class.java)
        }
    }

}