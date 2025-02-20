package com.example.myapplication.camara

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.BaseDatos
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityDondeBinding
import com.example.myapplication.databinding.ActivityDondeListarBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DondeListar : AppCompatActivity() {

    lateinit var binding: ActivityDondeListarBinding
    private val userDao by lazy { BaseDatos.getInstance(this).userDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDondeListarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username: String = intent.getStringExtra("USUARIO").toString()

        binding.textView25.text = username
        CoroutineScope(Dispatchers.IO).launch {
            val image: String = userDao.selectpicture(username)
            runOnUiThread {
                Picasso.get().load(image).into(binding.imageView6)
            }
        }


        binding.usuario.setOnClickListener{
            val intent = Intent(this, ListaFotos::class.java)
            intent.putExtra("USUARIO", username)
            intent.putExtra("CARPETA", username)
            startActivity(intent)
        }

        binding.comun.setOnClickListener{
            val intent = Intent(this, ListaFotos::class.java)
            intent.putExtra("USUARIO", username)
            intent.putExtra("CARPETA", "comunes")
            startActivity(intent)
        }


    }
}