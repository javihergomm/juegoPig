package com.example.myapplication.pig

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class NombresAdapter(
    private val nombres: List<String>,
    private val onNombreSelected: (String) -> Unit
) : RecyclerView.Adapter<NombresAdapter.NombreViewHolder>() {

    // Posición inicial sin selección
    private var selectedPosition = RecyclerView.NO_POSITION

    inner class NombreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textoNombres: TextView = itemView.findViewById(R.id.textoNombres)

        fun bind(nombre: String) {
            textoNombres.text = nombre

            // Cambia el estado visual del elemento seleccionado
            itemView.isSelected = adapterPosition == selectedPosition

            itemView.setOnClickListener {
                // Notifica el cambio en la posición seleccionada anterior y actual
                notifyItemChanged(selectedPosition)
                selectedPosition = adapterPosition
                notifyItemChanged(selectedPosition)

                // Llama al callback para notificar el nombre seleccionado
                onNombreSelected(nombre)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NombreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_name, parent, false)
        return NombreViewHolder(view)
    }

    override fun onBindViewHolder(holder: NombreViewHolder, position: Int) {
        holder.bind(nombres[position])
    }

    override fun getItemCount(): Int = nombres.size
}
