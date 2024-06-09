package com.whiteliontechnology.pampas.asistencia.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.whiteliontechnology.pampas.asistencia.model.Area

class AreaSpinnerAdapter(context: Context, areas: List<Area>) : ArrayAdapter<Area>(context, 0, areas) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val area = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_item, parent, false)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = area?.desAre
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val area = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = area?.desAre
        return view
    }

    override fun getItemId(position: Int): Long {
        // Devuelve el id de la posición
        return position.toLong()
    }

    override fun getItem(position: Int): Area? {
        // Devuelve el objeto Area en la posición
        return super.getItem(position)
    }
}
