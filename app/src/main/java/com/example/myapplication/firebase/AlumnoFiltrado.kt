package com.example.myapplication.firebase

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.BaseDatos
import com.example.myapplication.databinding.ActivityAlumnoFiltradoBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlumnoFiltrado : AppCompatActivity() {
    private lateinit var binding: ActivityAlumnoFiltradoBinding
    private val userDao by lazy { BaseDatos.getInstance(this).userDao() }
    private val db: FirebaseFirestore = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlumnoFiltradoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username: String = intent.getStringExtra("USUARIO").toString()
        val nia: String = intent.getStringExtra("NIA").toString()
        val nif: String = intent.getStringExtra("NIF").toString()

        binding.textView11.text = username
        CoroutineScope(Dispatchers.IO).launch {
            val image: String = userDao.selectpicture(username)
            runOnUiThread {
                Picasso.get().load(image).into(binding.imageView7)
            }
        }
        if (nia != "") {
            db.collection("alumnos")
                .whereEqualTo("nia", nia) // Cambiar a "nif" si buscas por NIF
                .get()
                .addOnSuccessListener { result ->
                    if (!result.isEmpty) {
                        val alumno = result.documents[0].toObject(Alumno::class.java)
                        mostrarAlumnoFiltrado(alumno)
                    }
                }.addOnFailureListener{ e ->
                    Log.e("Firestore", "Error al filtrar por NIA", e)
                }
        }else {
            db.collection("alumnos")
                .whereEqualTo("nif", nif)
                .get()
                .addOnSuccessListener { nifResult ->
                    if (!nifResult.isEmpty) {
                        val alumno = nifResult.documents[0].toObject(Alumno::class.java)
                        mostrarAlumnoFiltrado(alumno)
                    }
                }.addOnFailureListener{ e ->
                    Log.e("Firestore", "Error al filtrar por NIF", e)
                }
        }
    }
    @SuppressLint("SetTextI18n")
    private fun mostrarAlumnoFiltrado(alumno: Alumno?) {
        alumno?.let {
            binding.textView13.text =
                "Nombre: ${alumno.nombre}\nApellido: ${alumno.apellido}\nNIA: ${alumno.nia}\nNIF: ${alumno.nif}"
        }
    }
}
