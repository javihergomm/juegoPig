package com.example.myapplication

import android.content.Intent
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.audioPlayer.audioPlayer
import com.example.myapplication.camara.MenuCamara
import com.example.myapplication.chuk.MemesChuckNorris
import com.example.myapplication.databinding.ActivityHubBinding
import com.example.myapplication.firebase.ConOSin
import com.example.myapplication.pig.InicioPig
import com.example.myapplication.videoPlayer.ListaVideos
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Hub : AppCompatActivity() {
    lateinit var binding: ActivityHubBinding
    private val userDao by lazy { BaseDatos.getInstance(this).userDao() }
    private lateinit var soundPool: SoundPool
    private var soundId = 0
    private var streamId = 0
    private var isMuted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHubBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val username: String = intent.getStringExtra("USUARIO").toString()

        Handler(Looper.getMainLooper()).postDelayed({
            binding.lottieWelcome.visibility = View.GONE
        }, 3000)

        CoroutineScope(Dispatchers.IO).launch {
            val image: String = userDao.selectpicture(username)
            runOnUiThread {
                binding.lottieProfile.playAnimation()
                Picasso.get().load(image).into(binding.imageView)
            }
        }




        soundPool = SoundPool.Builder()
            .setMaxStreams(5)
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build()
            ).build()
        soundId = soundPool.load(this, R.raw.melody, 1)
        soundPool.setOnLoadCompleteListener{_, _, status ->
            if (status == 0){
                streamId = soundPool.play(soundId, 1.0f, 1.0f, 1, -1, 1.0f)
            }
        }

        binding.activarSonido.setOnClickListener{
            binding.activarSonido.visibility = View.GONE
            binding.desactivarSonido.visibility = View.VISIBLE
            soundPool.setVolume(streamId, 1.0f, 1.0f)
        }
        binding.desactivarSonido.setOnClickListener{
            binding.activarSonido.visibility = View.VISIBLE
            binding.desactivarSonido.visibility = View.GONE
            soundPool.setVolume(streamId, 0.0f, 0.0f)
        }


        binding.imageButton.setOnClickListener{
            val intent = Intent(this, InicioPig::class.java)
            startActivity(intent)
        }

        binding.imageButton2.setOnClickListener{
            val intent = Intent(this, MemesChuckNorris::class.java)
            startActivity(intent)
        }
        binding.imageButton3.setOnClickListener{
            val intent = Intent(this, ConOSin::class.java)
            intent.putExtra("USUARIO", username)
            startActivity(intent)
        }
        binding.imageButton8.setOnClickListener{
            val intent = Intent(this, MenuCamara::class.java)
            intent.putExtra("USUARIO", username)
            startActivity(intent)
        }
        binding.imageButton7.setOnClickListener{
            val intent = Intent(this, ListaVideos::class.java)
            intent.putExtra("USUARIO", username)
            startActivity(intent)
        }
        binding.imageButton9.setOnClickListener{
            val intent = Intent(this, audioPlayer::class.java)
            intent.putExtra("USUARIO", username)
            startActivity(intent)
        }



    }
    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }

    override fun onPause() {
        super.onPause()
        soundPool.pause(streamId)
    }

    override fun onResume() {
        super.onResume()
        soundPool.resume(streamId)
    }
}