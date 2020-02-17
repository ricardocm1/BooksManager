package com.android.booksmanager.network

import com.android.booksmanager.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private val retrofit: Retrofit
        get() {
            val httpClient = OkHttpClient.Builder()
            httpClient.readTimeout(180, TimeUnit.SECONDS)
            httpClient.connectTimeout(180, TimeUnit.SECONDS)

            httpClient.addInterceptor { chain ->
                val request = chain
                        .request()
                        .newBuilder()
                        .addHeader("Content-type", "application/json")
                        .build()

                chain.proceed(request)
            }

            return Retrofit.Builder()
                    .baseUrl(BuildConfig.HTTP_SERVER)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build()
        }

    fun bookManager() = retrofit.create(BooksService::class.java)!!
}
