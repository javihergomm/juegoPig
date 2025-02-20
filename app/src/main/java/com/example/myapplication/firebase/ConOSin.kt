package com.example.myapplication.firebase

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.BaseDatos
import com.example.myapplication.databinding.ActivityConOSinBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ConOSin : AppCompatActivity() {
    lateinit var binding: ActivityConOSinBinding
    private val userDao by lazy { BaseDatos.getInstance(this).userDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityConOSinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username: String = intent.getStringExtra("USUARIO").toString()
        binding.textView8.text = username
        CoroutineScope(Dispatchers.IO).launch {
            val image: String = userDao.selectpicture(username)
            runOnUiThread {
                Picasso.get().load(image).into(binding.imageView2)
            }
        }

        binding.button.setOnClickListener{
            val intent = Intent(this, SinAutenticacion::class.java)
            intent.putExtra("USUARIO", username)
            startActivity(intent)
        }

        binding.button2.setOnClickListener{
            val intent = Intent(this, ConAutenticacion::class.java)
            intent.putExtra("USUARIO", username)
            startActivity(intent)
        }


    }
}