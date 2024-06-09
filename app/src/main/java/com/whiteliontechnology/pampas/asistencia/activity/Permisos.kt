package com.whiteliontechnology.pampas.asistencia.activity

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.airbnb.lottie.LottieAnimationView
import com.whiteliontechnology.pampas.asistencia.R

class Permisos : AppCompatActivity() {

    private lateinit var lblMensaje: TextView
    private lateinit var btnPermiso: Button
    private lateinit var imgPrincipal: LottieAnimationView

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            imgPrincipal.setAnimation(R.raw.confirmacion)
            animate(imgPrincipal)
            lblMensaje.text = "Los permisos aceptados"
            btnPermiso.text = "Continuar"
            btnPermiso.setOnClickListener {
                val intent = Intent(this@Permisos, Login::class.java)
                startActivity(intent)
                finish()
            }
        } else {
            lblMensaje.text = "Los permisos NO han sido aceptados"
            btnPermiso.text = "Cerrar"
            btnPermiso.setOnClickListener {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permisos)

        supportActionBar?.hide()

        imgPrincipal = findViewById(R.id.imgPr)
        lblMensaje = findViewById(R.id.lblMensaje)
        btnPermiso = findViewById(R.id.btnPermisos)

        btnPermiso.setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    // para animar lottie
    private fun animate(view: View) {
        imgPrincipal.loop(true)
        imgPrincipal.playAnimation()
    }
}
