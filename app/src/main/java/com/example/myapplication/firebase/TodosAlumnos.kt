package com.example.myapplication.firebase

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.BaseDatos
import com.example.myapplication.databinding.ActivityTodosAlumnosBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodosAlumnos : AppCompatActivity() {
    private lateinit var binding: ActivityTodosAlumnosBinding
    private val userDao by lazy { BaseDatos.getInstance(this).userDao() }
    private val db: FirebaseFirestore = Firebase.firestore
    private lateinit var alumnoAdapter: AlumnosAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTodosAlumnosBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val username = intent.getStringExtra("USUARIO").toString()
        binding.textView9.text = username
        CoroutineScope(Dispatchers.IO).launch {
            val image: String = userDao.selectpicture(username)
            runOnUiThread {
                Picasso.get().load(image).into(binding.imageView3)
            }
        }
        val listaAlumnos = mutableListOf<Alumno>()


        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        alumnoAdapter = AlumnosAdapter(listaAlumnos)
        binding.recyclerView.adapter = alumnoAdapter

        db.collection("alumnos")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val alumno = document.toObject(Alumno::class.java)
                    listaAlumnos.add(alumno)
                }
                // Notifica al adaptador que los datos han cambiado
                alumnoAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error al obtener documentos", e)
            }

    }
}