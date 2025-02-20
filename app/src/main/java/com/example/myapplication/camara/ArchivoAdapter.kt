package com.example.myapplication.camara

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class ArchivoAdapter(
    private val listaArchivos: List<String>,
    private val onItemClick: (String) -> Unit // Callback para selección
) : RecyclerView.Adapter<ArchivoAdapter.ArchivoViewHolder>() {

    private val archivosSeleccionados = mutableSetOf<String>() // Archivos seleccionados

    class ArchivoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewArchivo: TextView = itemView.findViewById(R.id.textViewArchivo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArchivoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_archivo, parent, false)
        return ArchivoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArchivoViewHolder, position: Int) {
        val archivo = listaArchivos[position]
        holder.textViewArchivo.text = archivo

        // Cambiar color si está seleccionado
        holder.itemView.setBackgroundColor(
            if (archivosSeleccionados.contains(archivo)) Color.LTGRAY else Color.WHITE
        )

        // Manejar clic para selección
        holder.itemView.setOnClickListener {
            if (archivosSeleccionados.contains(archivo)) {
                archivosSeleccionados.remove(archivo)
            } else {
                archivosSeleccionados.add(archivo)
            }
            notifyItemChanged(position) // Refrescar item
            onItemClick(archivo) // Notificar selección
        }
    }

    override fun getItemCount(): Int = listaArchivos.size

    // Obtener archivos seleccionados
    fun getArchivosSeleccionados(): List<String> = archivosSeleccionados.toList()
}