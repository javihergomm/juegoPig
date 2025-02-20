package com.example.myapplication.videoPlayer

import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.ActivityReproducirVideoBinding

class ReproducirVideo : AppCompatActivity() {

    private lateinit var videoView: VideoView
    private lateinit var videoNombre: String
    private lateinit var username: String
    private lateinit var binding: ActivityReproducirVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReproducirVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        videoView = binding.videoView
        videoNombre = intent.getStringExtra("VIDEO_NOMBRE") ?: ""
        username = intent.getStringExtra("USUARIO") ?: "defaultUser"

        val videoUri = obtenerUriVideo(videoNombre)

        if (videoUri != null) {
            val mediaController = MediaController(this)
            mediaController.setAnchorView(videoView)
            videoView.setMediaController(mediaController)
            videoView.setVideoURI(videoUri)
            videoView.start()
        }
    }

    private fun obtenerUriVideo(nombre: String): Uri? {
        val videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val selection = "${MediaStore.Video.Media.DISPLAY_NAME} = ? AND ${MediaStore.Video.Media.RELATIVE_PATH} LIKE ?"
        val selectionArgs = arrayOf(nombre, "Movies/$username%")

        contentResolver.query(videoUri, arrayOf(MediaStore.Video.Media._ID), selection, selectionArgs, null)
            ?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val idColumn = cursor.getColumnIndex(MediaStore.Video.Media._ID)
                    val id = cursor.getLong(idColumn)
                    return Uri.withAppendedPath(videoUri, id.toString())
                }
            }

        return null
    }
}
