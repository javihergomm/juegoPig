package com.example.myapplication.firebase

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityFormBinding
import com.google.firebase.firestore.FirebaseFirestore

class FormActivity : AppCompatActivity() {
    private lateinit var binding:ActivityFormBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val etNIA: EditText = binding.etNIA
        val etNIF: EditText = binding.etNIF
        val etNombre: EditText = binding.etNombre
        val etApellido: EditText = binding.etApellido
        val etMedia: EditText = binding.etMedia
        val btnInsertar: Button = binding.btnInsertar

        btnInsertar.setOnClickListener {
            val nia = etNIA.text.toString()
            val nif = etNIF.text.toString()
            val nombre = etNombre.text.toString()
            val apellido = etApellido.text.toString()
            val media = etMedia.text.toString()

            if (nombre.isNotEmpty() && apellido.isNotEmpty() && nif.isNotEmpty() && nia.isNotEmpty() && media.isNotEmpty()) {
                db.collection("alumnos")
                    .whereEqualTo("nia", nia)
                    .get()
                    .addOnSuccessListener { documents ->
                        if (documents.isEmpty) {
                            // Insertar nuevo alumno
                            val alumno = Alumno(apellido, media, nif, nia, nombre)
                            db.collection("alumnos").add(alumno)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Alumno insertado correctamente", Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                                .addOnFailureListener { e ->
                                    Log.e("Firestore", "Error al insertar alumno", e)
                                }
                        } else {
                            Toast.makeText(this, "Alumno ya existente (NIA o NIF duplicado)", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
