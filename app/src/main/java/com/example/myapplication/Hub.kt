package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityHubBinding

class Hub : AppCompatActivity() {
    lateinit var binding: ActivityHubBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityHubBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val juegoPig: ImageButton = binding.imageButton

        juegoPig.setOnClickListener{
            val intent = Intent(this, InicioPig::class.java)
            startActivity(intent)
        }

    }
}