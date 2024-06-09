package com.whiteliontechnology.pampas.asistencia.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.whiteliontechnology.pampas.asistencia.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        if (ActivityCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            //VERIFICAR SI HAY DNI ALMACENADO
            val preferencias = getSharedPreferences("MisPreferencias", MODE_PRIVATE)
            val codTra = preferencias.getString("codTra", "nulo")
            if (codTra.equals("nulo", ignoreCase = true)) {
                val intent = Intent(this@MainActivity, Login::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this@MainActivity, ListarAreas::class.java)
                startActivity(intent)
                finish()
            }
        } else {
            val intent = Intent(this@MainActivity, Permisos::class.java)
            startActivity(intent)
            finish()
        }


        /*Crear un Intent para iniciar la actividad Permisos
        val intent = Intent(this, Permisos::class.java)
        startActivity(intent)
        finish() // Finalizar MainActivity para que no pueda volver atrás*/
    }
}