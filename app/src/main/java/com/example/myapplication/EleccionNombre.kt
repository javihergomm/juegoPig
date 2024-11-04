package com.example.myapplication

import NombresAdapter
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityMainBinding

class EleccionNombre : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_eleccion_nombre)

        var nombres = listOf("Aitor Tilla", "Ana Conda", "Armando Broncas", "Aurora Boreal",
            "Bartolo Mesa", "Carmen Mente", "Dolores Delirio", "Elsa Pato", "Enrique Cido",
            "Esteban Dido", "Elba Lazo", "Fermin Tado", "Lola Mento", "Luz Cuesta", "Margarita Flores",
            "Paco Tilla", "Pere Gil", "Pío Nono", "Salvador Tumbado", "Zoila Vaca")
        val recyclerView: RecyclerView = findViewById(R.id.NombresJug1)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = NombresAdapter(nombres)

    }
}