package com.whiteliontechnology.pampas.asistencia.common
import android.content.Context
import android.content.SharedPreferences

  const val PREF_NAME = "MisPreferencias"

  fun guardarDatosEnSharedPreferences(context: Context, numDocIde: String, codTra: String, nombres: String, codCia: String, nomCia: String, telefono: String, codArea : String , desAre : String){
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("numDocIde", numDocIde)
            putString("codTra", codTra)
            putString("nombres", nombres)
            putString("codCia", codCia)
            putString("nomCia", nomCia)
            putString("telefono", telefono)
            putString("codAre", codArea)
            putString("desAre", desAre)
            apply()
        }
    }

