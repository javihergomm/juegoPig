import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class NombresAdapter(private val nombres: List<String>) : RecyclerView.Adapter<NombresAdapter.NombreViewHolder>() {

    // ViewHolder para el RecyclerView
    class NombreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombresJug1: TextView = itemView.findViewById(R.id.NombresJug1)
    }

    // Crear y retornar el ViewHolder cuando se necesite uno nuevo
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NombreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_eleccion_nombre, parent, false)
        return NombreViewHolder(view)
    }

    // Enlazar los datos con el ViewHolder
    override fun onBindViewHolder(holder: NombreViewHolder, position: Int) {
        val name = nombres[position]
        holder.nombresJug1.text = name
    }

    override fun getItemCount(): Int {
        return nombres.size
    }
}
