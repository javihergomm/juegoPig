package com.example.myapplication.chuk

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMemesChuckNorrisBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MemesChuckNorris : AppCompatActivity() {
    private lateinit var binding: ActivityMemesChuckNorrisBinding
    private lateinit var opcionSeleccionada: String


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMemesChuckNorrisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categories = arrayOf("animal","career","celebrity","dev","explicit","fashion","food","history","money","movie","music","political","religion","science","sport","travel")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinner2.adapter = adapter


        binding.spinner2.setOnTouchListener { _, _ ->
            searchByCategory(opcionSeleccionada)
            false
        }


        binding.spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, p1: View?, p2: Int, p3: Long) {

                opcionSeleccionada = parent.getItemAtPosition(p2).toString()

                searchByCategory(opcionSeleccionada)

            }


            override fun onNothingSelected(p0: AdapterView<*>?) {}


        }

    }
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.chucknorris.io/jokes/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchByCategory(query: String){
        CoroutineScope(Dispatchers.IO).launch {
            val service = getRetrofit().create(ChuckNorrisService::class.java)
            val call = service.getMemeByCategory("random?category=$query")
            val response = call.execute()
            runOnUiThread{
                if (response.isSuccessful){
                    val memeChukNorris = response.body()?.value

                    binding.meme.text = memeChukNorris
                }
            }
        }

    }


}