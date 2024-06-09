package com.whiteliontechnology.pampas.asistencia.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import com.whiteliontechnology.pampas.asistencia.R
import com.whiteliontechnology.pampas.asistencia.common.Area
import com.whiteliontechnology.pampas.asistencia.common.PREF_NAME
import com.whiteliontechnology.pampas.asistencia.common.guardarDatosEnSharedPreferences
import io.realm.Realm
import io.realm.RealmResults

class ListarAreas : AppCompatActivity() {
    private lateinit var spArea : Spinner
    private lateinit var btnIngresar : Button
    private lateinit var lblNombres : TextView
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_areas)
        Realm.init(this)
        lblNombres = findViewById(R.id.lblNombres)
        val preferencias = getSharedPreferences("MisPreferencias", MODE_PRIVATE)
        val nombres = preferencias.getString("nombres", "nulo")
        lblNombres.text = nombres.toString()

        spArea = findViewById(R.id.spArea)
        realm = Realm.getDefaultInstance()
        val areas: RealmResults<Area> = realm.where(Area::class.java).findAll()
        val nombresAreas = areas.map { it.DesAre }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, nombresAreas)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spArea.adapter = adapter


        btnIngresar = findViewById(R.id.btnIngresar)
        btnIngresar.setOnClickListener {
            val selectedArea = spArea.selectedItem as Area

            // Obtener los valores actuales de numDocIde y codTra de SharedPreferences
            val sharedPreferences = applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            val numDocIde = sharedPreferences.getString("numDocIde", "") ?: ""
            val nombres = sharedPreferences.getString("nombres", "") ?: ""
            val codCia = sharedPreferences.getString("codCia", "") ?: ""
            val nomCia = sharedPreferences.getString("nomCia", "") ?: ""
            val telefono = sharedPreferences.getString("telefono", "") ?: ""
            val codTra = sharedPreferences.getString("codTra", "") ?: ""

            // Guardar el codAre y desAre seleccionados en SharedPreferences sin reemplazar numDocIde y codTra
            guardarDatosEnSharedPreferences(
                applicationContext,
                numDocIde,
                codTra,
                nombres,
                codCia,
                nomCia,
                telefono,
                selectedArea.CodAre,
                selectedArea.DesAre
            )
            val intent = Intent(this@ListarAreas, MenuPrincipal::class.java)
            val descArea = preferencias.getString("desAre", "nulo")
            Log.d("areaaaaa: " , descArea.toString())
            startActivity(intent)
            finish()
        }

        // Escuchar cambios en el Spinner spArea
        spArea.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Obtener el área seleccionada
                val selectedArea = areas[position]

                // Mostrar el código y la descripción del área seleccionada en el log
                if (selectedArea != null) {
                    Log.d("cod: ", selectedArea.CodAre.toString())
                }
                if (selectedArea != null) {
                    Log.d("areaaaaa: ", selectedArea.DesAre)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No es necesario hacer nada si no se ha seleccionado nada
            }
        }

    }



}
