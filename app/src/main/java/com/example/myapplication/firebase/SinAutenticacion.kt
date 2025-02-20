package com.example.myapplication.firebase

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.BaseDatos
import com.example.myapplication.databinding.ActivitySinAutenticacionBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SinAutenticacion : AppCompatActivity() {
    private lateinit var binding: ActivitySinAutenticacionBinding
    private val userDao by lazy { BaseDatos.getInstance(this).userDao() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySinAutenticacionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username: String = intent.getStringExtra("USUARIO").toString()
        binding.textView10.text = username
        CoroutineScope(Dispatchers.IO).launch {
            val image: String = userDao.selectpicture(username)
            runOnUiThread {
                Picasso.get().load(image).into(binding.imageView5)
            }
        }



        binding.button6.setOnClickListener{

            val intent = Intent(this, TodosAlumnos::class.java)
            intent.putExtra("USUARIO", username)
            startActivity(intent)

        }
        binding.button5.setOnClickListener{
            val texto = binding.editTextText2.text.toString()
            if (binding.editTextText2.text.isEmpty()){
                binding.textView12.visibility = View.VISIBLE
            }else{
                val intent = Intent(this, AlumnoFiltrado::class.java)
                intent.putExtra("USUARIO", username)
                intent.putExtra("NIF", texto)
                intent.putExtra("NIA", "")
                startActivity(intent)
            }

        }
        binding.button7.setOnClickListener{
            val texto = binding.editTextText2.text.toString()
            if (binding.editTextText2.text.isEmpty()){
                binding.textView12.visibility = View.VISIBLE
            }else{
                val intent = Intent(this, AlumnoFiltrado::class.java)
                intent.putExtra("USUARIO", username)
                intent.putExtra("NIA", texto)
                intent.putExtra("NIF", "")
                startActivity(intent)
            }

        }


    }
}