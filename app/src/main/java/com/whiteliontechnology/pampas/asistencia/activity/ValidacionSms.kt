package com.whiteliontechnology.pampas.asistencia.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.whiteliontechnology.pampas.asistencia.R

class ValidacionSms : AppCompatActivity() {
    private lateinit var btnIngresar: Button
    private lateinit var lblNombres : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_validacion_sms)

        lblNombres = findViewById(R.id.lblNombres)
        val preferencias = getSharedPreferences("MisPreferencias", MODE_PRIVATE)
        val nombres = preferencias.getString("nombres", "nulo")
        lblNombres.setText(nombres.toString())

        btnIngresar = findViewById(R.id.btnIngresar)
        btnIngresar.setOnClickListener {
            val intent = Intent(this@ValidacionSms, ListarAreas::class.java)
            startActivity(intent)
            finish()
        }

    }
}