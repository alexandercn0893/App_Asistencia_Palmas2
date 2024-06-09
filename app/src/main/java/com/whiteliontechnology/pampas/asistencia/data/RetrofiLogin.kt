package com.whiteliontechnology.pampas.asistencia.data

import com.google.gson.GsonBuilder
import com.whiteliontechnology.pampas.asistencia.model.LoginData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface RetrofitService {
    @POST("persona/verificarDatos")
    fun verificarDatos(@Header("Authorization") token: String, @Body params: Map<String, String>): Call<List<LoginData>>

    companion object {
        fun create(): RetrofitService {
            val gson = GsonBuilder().setLenient().create()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api2personnelplus.whitelion.pe:8187/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit.create(RetrofitService::class.java)
        }
    }
}
