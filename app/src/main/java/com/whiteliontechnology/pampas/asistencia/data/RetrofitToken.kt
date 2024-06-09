package com.whiteliontechnology.pampas.asistencia.data

import com.google.gson.GsonBuilder
import com.whiteliontechnology.pampas.asistencia.model.LoginRequest
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitToken {

    @POST("login/token")
    fun verificarToken(@Body request: LoginRequest): Call<String>

    companion object {
        fun create(): RetrofitToken {
            val gson = GsonBuilder().setLenient().create()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api2personnelplus.whitelion.pe:8187/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit.create(RetrofitToken::class.java)
        }
    }
}

