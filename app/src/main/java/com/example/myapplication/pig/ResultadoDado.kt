package com.example.myapplication.pig

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityResultadoDadoBinding

class ResultadoDado : AppCompatActivity() {
    private lateinit var binding: ActivityResultadoDadoBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResultadoDadoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val listaFinal = intent.getStringArrayListExtra("LISTA")
        if (listaFinal != null) {
            binding.textoGanador.text = listaFinal[0]
            binding.textoPuntosJ1.text = listaFinal[1]
            binding.textoPuntosJ2.text = listaFinal[2]
            if (listaFinal.size == 4){
                binding.textoPuntosJ3.text = listaFinal[3] ?: ""
                binding.textoPuntosJ3.visibility = View.VISIBLE
            }else if (listaFinal.size == 5){
                binding.textoPuntosJ4.visibility = View.VISIBLE
                binding.textoPuntosJ4.text = listaFinal[4] ?: ""
            }
        }




    }
}