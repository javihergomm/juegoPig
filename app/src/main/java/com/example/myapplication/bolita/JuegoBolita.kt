package com.example.myapplication.bolita

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.BaseDatos
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityJuegoBolitaBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JuegoBolita : AppCompatActivity() {
    private lateinit var binding: ActivityJuegoBolitaBinding
    private val userDao by lazy { BaseDatos.getInstance(this).userDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityJuegoBolitaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username: String = intent.getStringExtra("USUARIO").toString()
        binding.textView28.text = username
        CoroutineScope(Dispatchers.IO).launch {
            val image: String = userDao.selectpicture(username)
            runOnUiThread {
                Picasso.get().load(image).into(binding.imageView8)
            }
        }


    }
}