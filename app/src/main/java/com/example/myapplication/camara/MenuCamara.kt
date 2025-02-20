package com.example.myapplication.camara

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.BaseDatos
import com.example.myapplication.databinding.ActivityHubBinding
import com.example.myapplication.databinding.ActivityMenuCamaraBinding
import com.example.myapplication.pig.InicioPig
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuCamara : AppCompatActivity() {
    lateinit var binding: ActivityMenuCamaraBinding
    private val userDao by lazy { BaseDatos.getInstance(this).userDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuCamaraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val username: String = intent.getStringExtra("USUARIO").toString()

        binding.textView16.text = username
        CoroutineScope(Dispatchers.IO).launch {
            val image: String = userDao.selectpicture(username)
            runOnUiThread {
                Picasso.get().load(image).into(binding.imageView4)
            }
        }

        binding.button3.setOnClickListener{

            val intent = Intent(this, Donde::class.java)
            intent.putExtra("USUARIO", username)
            startActivity(intent)

        }

        binding.button4.setOnClickListener{
            val intent = Intent(this, DondeListar::class.java)
            intent.putExtra("USUARIO", username)
            startActivity(intent)
        }


    }


}