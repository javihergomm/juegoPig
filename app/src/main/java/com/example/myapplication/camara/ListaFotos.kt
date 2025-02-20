package com.example.myapplication.camara

import android.content.ContentResolver
import android.content.ContentUris
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityListaFotosBinding

class ListaFotos : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var archivoAdapter: ArchivoAdapter
    private lateinit var btnBorrar: Button
    private lateinit var username: String
    private lateinit var carpeta: String
    private val archivosSeleccionados = mutableListOf<String>()
    private lateinit var binding: ActivityListaFotosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaFotosBinding.inflate(layoutInflater)

        setContentView(binding.root)

        recyclerView = binding.recyclerView2
        btnBorrar = binding.button8
        recyclerView.layoutManager = LinearLayoutManager(this)

        username = intent.getStringExtra("USUARIO") ?: "defaultUser"
        carpeta= intent.getStringExtra("CARPETA").toString()

        val listaDeArchivos = obtenerArchivosMediaStore()
        archivoAdapter = ArchivoAdapter(listaDeArchivos) { archivo ->
            manejarSeleccion(archivo)
        }

        recyclerView.adapter = archivoAdapter

        btnBorrar.setOnClickListener {
            eliminarArchivosSeleccionados()
        }
    }

    private fun manejarSeleccion(archivo: String) {
        if (archivosSeleccionados.contains(archivo)) {
            archivosSeleccionados.remove(archivo)
        } else {
            archivosSeleccionados.add(archivo)
        }
    }

    private fun obtenerArchivosMediaStore(): List<String> {
        val archivos = mutableListOf<String>()

        // Consultar imágenes
        val imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val imageSelection = "${MediaStore.Images.Media.RELATIVE_PATH} LIKE ?"
        val imageArgs = arrayOf("Pictures/$carpeta%")
        contentResolver.query(imageUri, arrayOf(MediaStore.Images.Media.DISPLAY_NAME), imageSelection, imageArgs, null)
            ?.use { cursor ->
                val nameColumn = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
                while (cursor.moveToNext()) {
                    archivos.add(cursor.getString(nameColumn))
                }
            }

        // Consultar videos
        val videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val videoSelection = "${MediaStore.Video.Media.RELATIVE_PATH} LIKE ?"
        val videoArgs = arrayOf("Movies/$carpeta%")
        contentResolver.query(videoUri, arrayOf(MediaStore.Video.Media.DISPLAY_NAME), videoSelection, videoArgs, null)
            ?.use { cursor ->
                val nameColumn = cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME)
                while (cursor.moveToNext()) {
                    archivos.add(cursor.getString(nameColumn))
                }
            }

        return archivos
    }

    private fun eliminarArchivosSeleccionados() {
        val resolver: ContentResolver = contentResolver
        var archivosBorrados = 0

        for (archivo in archivosSeleccionados) {
            // Eliminar de imágenes
            val imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val imageSelection = "${MediaStore.Images.Media.DISPLAY_NAME} = ?"
            val imageArgs = arrayOf(archivo)
            val rowsDeleted = resolver.delete(imageUri, imageSelection, imageArgs)
            archivosBorrados += rowsDeleted

            // Eliminar de videos
            val videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            val videoSelection = "${MediaStore.Video.Media.DISPLAY_NAME} = ?"
            val videoArgs = arrayOf(archivo)
            archivosBorrados += resolver.delete(videoUri, videoSelection, videoArgs)
        }

        Toast.makeText(this, "Se eliminaron $archivosBorrados archivos", Toast.LENGTH_SHORT).show()

        // Recargar la lista
        archivosSeleccionados.clear()
        val listaDeArchivos = obtenerArchivosMediaStore()
        archivoAdapter = ArchivoAdapter(listaDeArchivos) { archivo ->
            manejarSeleccion(archivo)
        }
        recyclerView.adapter = archivoAdapter
    }
}
