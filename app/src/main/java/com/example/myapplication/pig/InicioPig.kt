package com.example.myapplication.pig

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityIniciopigBinding
class InicioPig : AppCompatActivity() {
    private lateinit var binding: ActivityIniciopigBinding

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_iniciopig)
        binding = ActivityIniciopigBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val spinner: Spinner = binding.spinner
        val campoRondas: EditText = binding.CampoRondas
        val numeros = arrayOf("Selecciona una opción...", 2, 3, 4)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, numeros)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter

        var numJugadores: Int
        var numRondas: Int

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val opcionSeleccionada = parent.getItemAtPosition(position).toString()
                numRondas = campoRondas.text.toString().toIntOrNull() ?: 5



                if (opcionSeleccionada != "Selecciona una opción...") {
                    numJugadores = binding.spinner.selectedItem.toString().toInt()

                    val intent = Intent(parent.context, EleccionNombre::class.java)
                    intent.putExtra("NUM_JUGADORES", numJugadores)
                    intent.putExtra("NUM_RONDAS", numRondas)
                    startActivity(intent)
                }
            }


            override fun onNothingSelected(p0: AdapterView<*>?) {}


        }


    }
}