package com.whiteliontechnology.pampas.asistencia.activity

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.whiteliontechnology.pampas.asistencia.R
import com.whiteliontechnology.pampas.asistencia.common.Area
import com.whiteliontechnology.pampas.asistencia.common.cargando
import com.whiteliontechnology.pampas.asistencia.common.guardarDatosEnSharedPreferences
import com.whiteliontechnology.pampas.asistencia.common.mensaje
import com.whiteliontechnology.pampas.asistencia.data.RetrofitService
import com.whiteliontechnology.pampas.asistencia.data.RetrofitToken
import com.whiteliontechnology.pampas.asistencia.model.LoginData
import com.whiteliontechnology.pampas.asistencia.model.LoginRequest
import io.realm.Realm
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Login : AppCompatActivity() {

    private var txtUsuario: TextView? = null
    private lateinit var btnVerificar: Button
    var token = ""
    private lateinit var loadingDialog: Dialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Realm.init(this)
        txtUsuario = findViewById(R.id.txtUsuario)
        obtenerToken()

        btnVerificar = findViewById(R.id.btnVerificar)
        btnVerificar.setOnClickListener {
            IniciarSesion()
        }
    }

    fun obtenerToken() {
        val service = RetrofitToken.create()
        val request = LoginRequest("2PersonnelPalmas", "P@lmA\$2o2&")
        val call = service.verificarToken(request)

        call.enqueue(object : Callback<String> { // Cambia LoginRequest a String
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    // Aquí obtienes el token JWT del cuerpo de la respuesta
                    Log.d("LoginActivity", "Token obtenido: $responseBody")
                    token = responseBody.toString()
                    // Ahora puedes usar este token para hacer otras solicitudes que requieran autenticación
                } else {
                    Log.e("LoginActivity", "Error en la respuesta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("LoginActivity", "Error en la llamada: ${t.message}")
            }
        })

    }

    fun IniciarSesion(){

        val dni = txtUsuario!!.text.toString()
       /* if (dni.length != 8) {
            mensaje("error", this, "ERROR","El documento de identidad debe tener 8 digitos")
            return
        }*/
        loadingDialog = cargando(this, "Cargando...", "tipo")
        Log.d("DNIII", dni)
        //02773321
        val service = RetrofitService.create()
        val params = mapOf("NumDocIde" to dni)
        // val token = "Bearer eyJhbGciOiJBMTI4Q0JDLUhTMjU2IiwidHlwIjoiSldUIn0.eyJ1bmlxdWVfbmFtZSI6IjJQZXJzb25uZWxQYWxtYXMiLCJyb2xlIjoiQWRtaW5pc3RyYXRvciIsIm5iZiI6MTcxNzU0MDA2OSwiZXhwIjoxNzE3NTQxODY5LCJpYXQiOjE3MTc1NDAwNjksImlzcyI6Imh0dHBzOi8vYXBpMnBlcnNvbm5lbHBsdXMud2hpdGVsaW9uLnBlOjgxODciLCJhdWQiOiJodHRwczovL2FwaTJwZXJzb25uZWxwbHVzLndoaXRlbGlvbi5wZTo4MTg3In0.guXCBFf3-llj6Q8CFm8EeIzc9K1YZEsaH6Iqhv8Js9s"

        val call = service.verificarDatos(token, params)

        call.enqueue(object : Callback<List<LoginData>> {
            override fun onResponse(call: Call<List<LoginData>>, response: Response<List<LoginData>>) {
                if (response.isSuccessful) {
                    val responseData = response.body()
                    Log.d("LoginActivity", "Datos obtenidos: $responseData")

                    responseData?.forEach { loginData ->
                        val numDocIde = loginData.numDocIde
                        val codTra = loginData.codTra
                        val nombres = loginData.nombres
                        val codCia = loginData.codCia
                        val nomCia = loginData.nomCia
                        val telefono = loginData.telefono ?: "0"
                        val codAre = loginData.telefono ?: ""
                        val desAre = loginData.telefono ?: ""

                        guardarDatosEnSharedPreferences(this@Login, numDocIde, codTra, nombres, codCia, nomCia, telefono, codAre, desAre)

                        // Guardar áreas en Realm
                        Realm.getDefaultInstance().executeTransactionAsync { realm ->
                            loginData.areas.forEach { areaData ->
                                val area = Area().apply {
                                    CodAre = areaData.codAre
                                    CodTra = areaData.codTra
                                    DesAre = areaData.desAre
                                    Latitud = areaData.latitud
                                    Longitud = areaData.longitud
                                    Radio = areaData.radio
                                }
                                realm.insertOrUpdate(area)
                            }
                        }


                    }
                    // Luego puedes iniciar la siguiente actividad si es necesario
                    val intent = Intent(this@Login, ValidacionSms::class.java)
                    loadingDialog.dismiss()
                    startActivity(intent)
                    finish()
                } else {
                    loadingDialog.dismiss()
                    Log.e("LoginActivity", "Error en la respuesta: ${response.code()}")
                    mensaje("error", this@Login, "ERROR", "Usuario Inactivo")

                    return

                }
            }

            override fun onFailure(call: Call<List<LoginData>>, t: Throwable) {
                loadingDialog.dismiss()
                mensaje("error", this@Login, "ERROR", "${t.message}")
                Log.e("LoginActivity", "Error en la llamada: ${t.message}")
            }
        })
     }


}
