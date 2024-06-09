package com.whiteliontechnology.pampas.asistencia.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.RelativeLayout
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.whiteliontechnology.pampas.asistencia.R
import com.whiteliontechnology.pampas.asistencia.activity.Login

class MenuPrincipal : AppCompatActivity() {


    private lateinit var ivMenu: ImageView
    private lateinit var txtEstadoRed: TextView
    private lateinit var btnVerAsistencia: RelativeLayout
    private lateinit var lblNombres : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)

        ivMenu = findViewById(R.id.ltHamburguesa)
        txtEstadoRed = findViewById(R.id.txtEstadoRed)
        btnVerAsistencia = findViewById(R.id.btnVerAsistencia)
        lblNombres = findViewById(R.id.lblNombres)

        val preferencias = getSharedPreferences("MisPreferencias", MODE_PRIVATE)
        val nombres = preferencias.getString("nombres", "nulo")
        lblNombres.setText(nombres.toString())

        ivMenu.setOnClickListener {
            val menu = PopupMenu(this, ivMenu)
            menu.menuInflater.inflate(R.menu.menu_item, menu.menu)
            menu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.cerrar_sesion -> {
                        cerrarSesion()
                        true
                    }
                    R.id.cambiar_area -> {
                        // Intent para lanzar la actividad ListarAreas
                        val intent = Intent(this, ListarAreas::class.java)
                        startActivity(intent)
                        true
                    }

                    else -> false
                }
            }
            menu.show()
        }

        btnVerAsistencia.setOnClickListener {
            val intent = Intent(this, ListarMarcaciones::class.java)
            startActivity(intent)
        }

        verificarConexionInternet()


}

    private fun verificarConexionInternet() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected) {
            // Hay conexión a internet, establece el texto como "Conectado a Internet"
            txtEstadoRed.text = " Conectado a Internet"
            // Establece el color del texto en verde (success)
            txtEstadoRed.setTextColor(resources.getColor(R.color.colorSuccess))
            // Agrega la imagen de check al inicio del texto
            val drawableCheck = getDrawable(R.drawable.ic_wifi)
            drawableCheck?.setBounds(0, 0, drawableCheck.intrinsicWidth, drawableCheck.intrinsicHeight)
            txtEstadoRed.setCompoundDrawables(drawableCheck, null, null, null)
        } else {
            // No hay conexión a internet, establece el texto como "Sin conexión a Internet"
            txtEstadoRed.text = " Sin conexión a Internet"
            // Establece el color del texto en rojo (danger)
            txtEstadoRed.setTextColor(resources.getColor(R.color.colorDanger))
            // Agrega la imagen de cross al inicio del texto
            val drawableCross = getDrawable(R.drawable.ic_wifi_offline)
            drawableCross?.setBounds(0, 0, drawableCross.intrinsicWidth, drawableCross.intrinsicHeight)
            txtEstadoRed.setCompoundDrawables(drawableCross, null, null, null)
        }
    }


    private fun cerrarSesion() {
        // Infla el diseño personalizado
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.mensaje_confirmacion, null)

        // Encuentra las vistas dentro del diseño inflado
        val ltTipoConfirmacion: LottieAnimationView = view.findViewById(R.id.ltTipoConfirmacion)
        val txtMensajeConfirmacion: TextView = view.findViewById(R.id.txtMensajeConfirmacion)
        val btnCancelarAdvertencia: Button = view.findViewById(R.id.btnCancelarAdvertencia)
        val btnAceptarConfirmacion: Button = view.findViewById(R.id.btnAceptarConfirmacion)

        // Establece el mensaje en el TextView
        txtMensajeConfirmacion.text = "¿Quiere cerrar sesión?"

        // Establece la animación en el LottieAnimationView
        ltTipoConfirmacion.setAnimation(R.raw.exit)
        ltTipoConfirmacion.playAnimation() // Inicia la animación inmediatamente


        // Configura el AlertDialog
        val builder = AlertDialog.Builder(this)
        builder.setView(view)

        val dialog = builder.create()

        // Configura los botones del diseño inflado
        btnCancelarAdvertencia.setOnClickListener {
            dialog.dismiss()
        }

        btnAceptarConfirmacion.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finishAffinity()
            dialog.dismiss()
        }

        // Muestra el diálogo
        dialog.show()
    }

}