package com.example.myapplication

import NombresAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var turno: Int = 1
    var numRondas: Int = 1

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val spinner: Spinner = binding.spinner
        val numeros = arrayOf("Selecciona una opción...", 2, 3, 4)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, numeros)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter

        var numJugadores: Int

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val opcionSeleccionada = parent.getItemAtPosition(position).toString()

                if (opcionSeleccionada != "Selecciona una opción...") {
                    numJugadores = binding.spinner.selectedItem.toString().toInt()

                    val intent = Intent(parent.context, EleccionNombre::class.java)
                    intent.putExtra("NUM_JUGADORES", numJugadores)
                    startActivity(intent)
                }
            }


            override fun onNothingSelected(p0: AdapterView<*>?) {}


        }


    }
}