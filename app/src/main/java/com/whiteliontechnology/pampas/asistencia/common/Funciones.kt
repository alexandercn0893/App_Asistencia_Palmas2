package com.whiteliontechnology.pampas.asistencia.common

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.whiteliontechnology.pampas.asistencia.R
import java.util.concurrent.atomic.AtomicBoolean


fun cargando(context: Context, mensaje: String, tipo: String): Dialog {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.loading)

        val txtMensaje = dialog.findViewById<TextView>(R.id.txtMensajeLoading)
        val img = dialog.findViewById<LottieAnimationView>(R.id.ltCargandoLoading)

        if (mensaje.isNotEmpty()) {
            txtMensaje.text = mensaje
        } else {
            txtMensaje.visibility = View.GONE
        }

        if (tipo.isNotEmpty()) {
            img.setAnimation(R.raw.sincronizando)
        }

        val window = dialog.window
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if (!dialog.isShowing) {
            dialog.show()
        }

        return dialog

    }


fun mensaje(tipo: String, context: Context, titulo: String, mensaje: String): Boolean {
    val mResult = AtomicBoolean(false)

    val myDialog = Dialog(context)
    myDialog.setContentView(R.layout.dialog_mensaje)

    val tit = myDialog.findViewById<TextView>(R.id.txtTiuloError)
    val img = myDialog.findViewById<LottieAnimationView>(R.id.imgTipoMensaje)

    when (tipo) {
        "error" -> {
            img.setAnimation(R.raw.error)
            tit.text = titulo
        }

        "exito" -> img.setAnimation(R.raw.check)
        "advertencia" -> img.setAnimation(R.raw.advertencia)
    }

    myDialog.findViewById<TextView>(R.id.txtMensaje).text = mensaje

    myDialog.findViewById<Button>(R.id.btnAceptar).setOnClickListener {
        mResult.set(true)
        myDialog.dismiss()
    }

    val window = myDialog.window
    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    myDialog.show()

    return mResult.get()
}

fun selectedItemSpinner(spinner: Spinner, itemSelection: String) {
    var position = 0
    for (i in 0 until spinner.count) {
        if (spinner.getItemAtPosition(i) == itemSelection) {
            position = i
            break
        }
    }
    spinner.setSelection(position)
}

