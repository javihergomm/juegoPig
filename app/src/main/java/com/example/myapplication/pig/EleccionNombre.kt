package com.example.myapplication.pig

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityEleccionNombreBinding

class EleccionNombre() : AppCompatActivity() {
    private lateinit var binding: ActivityEleccionNombreBinding
    private var nombreSeleccionadoJug1: String? = null
    private var nombreSeleccionadoJug2: String? = null
    private var nombreSeleccionadoJug3: String? = null
    private var nombreSeleccionadoJug4: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_eleccion_nombre)
        binding = ActivityEleccionNombreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val numJugadores = intent.getIntExtra("NUM_JUGADORES", 0)
        val numRondas = intent.getIntExtra("NUM_RONDAS", 0)
        val textoJugador3: TextView = binding.TextoJugador3
        val textoJugador4: TextView = binding.TextoJugador4
        val botonIniciar: Button = binding.botonIniciar
        val textoError: TextView = binding.Error

        val nombres = listOf("Aitor Tilla", "Ana Conda", "Armando Broncas", "Aurora Boreal",
            "Bartolo Mesa", "Carmen Mente", "Dolores Delirio", "Elsa Pato", "Enrique Cido",
            "Esteban Dido", "Elba Lazo", "Fermin Tado", "Lola Mento", "Luz Cuesta", "Margarita Flores",
            "Paco Tilla", "Pere Gil", "Pío Nono", "Salvador Tumbado", "Zoila Vaca")



        val recyclerView1: RecyclerView = binding.NombresJug1
        recyclerView1.layoutManager = LinearLayoutManager(this)
        recyclerView1.adapter = NombresAdapter(nombres){ nombre ->
            nombreSeleccionadoJug1 = nombre
        }

        val recyclerView2: RecyclerView = binding.NombresJug2
        recyclerView2.layoutManager = LinearLayoutManager(this)
        recyclerView2.adapter = NombresAdapter(nombres){ nombre ->
            nombreSeleccionadoJug2 = nombre
        }

        val recyclerView3: RecyclerView = binding.NombresJug3
        recyclerView3.layoutManager = LinearLayoutManager(this)
        recyclerView3.adapter = NombresAdapter(nombres){ nombre ->
            nombreSeleccionadoJug3 = nombre
        }

        val recyclerView4: RecyclerView = binding.NombresJug4
        recyclerView4.layoutManager = LinearLayoutManager(this)
        recyclerView4.adapter = NombresAdapter(nombres){ nombre ->
            nombreSeleccionadoJug4 = nombre
        }

        if (numJugadores == 3){
            textoJugador3.visibility = View.VISIBLE
            recyclerView3.visibility = View.VISIBLE
        }else if (numJugadores == 4){
            textoJugador3.visibility = View.VISIBLE
            textoJugador4.visibility = View.VISIBLE
            recyclerView3.visibility = View.VISIBLE
            recyclerView4.visibility = View.VISIBLE
        }

        botonIniciar.setOnClickListener{
            if (nombreSeleccionadoJug1 != null && nombreSeleccionadoJug2 != null &&
                (numJugadores < 3 || nombreSeleccionadoJug3 != null) &&
                (numJugadores < 4 || nombreSeleccionadoJug4 != null)) {
                val intent = Intent(this, Partida::class.java)
                intent.putExtra("NUM_JUGADORES", numJugadores)
                intent.putExtra("NUM_RONDAS", numRondas)
                intent.putExtra("NOMBRE_JUG1", nombreSeleccionadoJug1)
                intent.putExtra("NOMBRE_JUG2", nombreSeleccionadoJug2)
                if (numJugadores >= 3) intent.putExtra("NOMBRE_JUG3", nombreSeleccionadoJug3)
                if (numJugadores == 4) intent.putExtra("NOMBRE_JUG4", nombreSeleccionadoJug4)
                startActivity(intent)
            }else{
                textoError.visibility = View.VISIBLE
            }
        }

    }
}