package com.example.myapplication.videoPlayer

import android.content.ContentResolver
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.camara.ArchivoAdapter
import com.example.myapplication.databinding.ActivityListaFotosBinding
import com.example.myapplication.databinding.ActivityListaVideosBinding

class ListaVideos : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var videoAdapter: ArchivoAdapter
    private lateinit var username: String
    private lateinit var binding: ActivityListaVideosBinding
    private val archivosSeleccionados = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaVideosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerviewvideos
        recyclerView.layoutManager = LinearLayoutManager(this)

        username = intent.getStringExtra("USUARIO") ?: "defaultUser"

        val listaDeVideos = obtenerVideos()

        videoAdapter = ArchivoAdapter(listaDeVideos) { videoNombre ->
            manejarSeleccion(videoNombre)
        }

        recyclerView.adapter = videoAdapter

        binding.borrar.setOnClickListener{
            eliminarArchivosSeleccionados()
        }
        binding.ver.setOnClickListener{
            if(archivosSeleccionados.size > 1){
                Toast.makeText(this, "Selecciona 1 único archivo", Toast.LENGTH_SHORT).show()
            }else if(archivosSeleccionados.size < 1){
                Toast.makeText(this, "Selecciona 1 único archivo", Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(this, ReproducirVideo::class.java)
                intent.putExtra("VIDEO_NOMBRE", archivosSeleccionados[0])
                intent.putExtra("USUARIO", username)
                startActivity(intent)
            }
        }

    }

    private fun obtenerVideos(): List<String> {
        val videos = mutableListOf<String>()

        val videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val selection = "${MediaStore.Video.Media.RELATIVE_PATH} LIKE ?"
        val selectionArgs = arrayOf("Movies/$username%")

        contentResolver.query(videoUri, arrayOf(MediaStore.Video.Media.DISPLAY_NAME), selection, selectionArgs, null)
            ?.use { cursor ->
                val nameColumn = cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME)
                while (cursor.moveToNext()) {
                    videos.add(cursor.getString(nameColumn))
                }
            }

        return videos
    }
    private fun manejarSeleccion(archivo: String) {
        if (archivosSeleccionados.contains(archivo)) {
            archivosSeleccionados.remove(archivo)
        } else {
            archivosSeleccionados.add(archivo)
        }
    }

    private fun eliminarArchivosSeleccionados() {
        val resolver: ContentResolver = contentResolver
        var archivosBorrados = 0

        for (archivo in archivosSeleccionados) {
            // Eliminar de videos
            val videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            val videoSelection = "${MediaStore.Video.Media.DISPLAY_NAME} = ?"
            val videoArgs = arrayOf(archivo)
            archivosBorrados += resolver.delete(videoUri, videoSelection, videoArgs)
        }

        Toast.makeText(this, "Se eliminaron $archivosBorrados archivos", Toast.LENGTH_SHORT).show()

        // Recargar la lista
        archivosSeleccionados.clear()
        val listaDeArchivos = obtenerVideos()
        videoAdapter = ArchivoAdapter(listaDeArchivos) { archivo ->
            manejarSeleccion(archivo)
        }
        recyclerView.adapter = videoAdapter
    }
}