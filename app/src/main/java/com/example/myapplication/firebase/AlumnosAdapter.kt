package com.example.myapplication.firebase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class AlumnosAdapter(private val listaAlumnos: List<Alumno>) : RecyclerView.Adapter<AlumnosAdapter.AlumnoViewHolder>() {

    class AlumnoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.textViewNombre)
        val tvApellido: TextView = view.findViewById(R.id.textViewApellido)
        val tvMedia: TextView = view.findViewById(R.id.textViewMedia)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlumnoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_alumno, parent, false)
        return AlumnoViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlumnoViewHolder, position: Int) {
        val alumno = listaAlumnos[position]
        holder.tvNombre.text = alumno.nombre
        holder.tvApellido.text = alumno.apellido
        holder.tvMedia.text = "MEDIA: ${alumno.mediaexpediente}"
    }

    override fun getItemCount(): Int = listaAlumnos.size
}