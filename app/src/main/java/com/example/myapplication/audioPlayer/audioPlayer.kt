package com.example.myapplication.audioPlayer

import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityAudioPlayerBinding
import java.util.Random

class audioPlayer : AppCompatActivity() {
    private lateinit var binding: ActivityAudioPlayerBinding
    private var selecionar: Boolean = false;
    private lateinit var soundPool: SoundPool
    private var soundId = 0
    private var streamId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAudioPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val velocidades = listOf("x1", "x2", "x1.5", "x0.5", "x0.25")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, velocidades)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinner3.adapter = adapter
        soundPool = SoundPool.Builder()
            .setMaxStreams(5)
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build()
            ).build()

        binding.soloDch.setOnClickListener{
            if (binding.soloIzq.isChecked){
                binding.soloIzq.isChecked = false
            }
        }
        binding.soloIzq.setOnClickListener{
            if (binding.soloDch.isChecked){
                binding.soloDch.isChecked = false
            }
        }

        binding.lingaguli.setOnClickListener{

            soundPool.stop(streamId)
            soundId = soundPool.load(this, R.raw.linganguliguli, 1)
            soundPool.setOnLoadCompleteListener{_, _, status ->
                if (status == 0){
                    streamId = soundPool.play(soundId, 1.0f, 1.0f, 1, 0, valorSpinner())
                }
                if (binding.soloDch.isChecked){
                    soundPool.setVolume(streamId, 0.0f, 1.0f)
                }
                if (binding.soloIzq.isChecked){
                    soundPool.setVolume(streamId, 1.0f, 0.0f)
                }
                if (binding.bucle.isChecked){
                    soundPool.setLoop(streamId, -1)
                }
            }

            hola


        }

        binding.vacinojose.setOnClickListener{

            soundPool.stop(streamId)
            soundId = soundPool.load(this, R.raw.mivecinojose, 1)
            soundPool.setOnLoadCompleteListener{_, _, status ->
                if (status == 0){
                    streamId = soundPool.play(soundId, 1.0f, 1.0f, 1, 0, valorSpinner())
                }
                if (binding.soloDch.isChecked){
                    soundPool.setVolume(streamId, 0.0f, 1.0f)
                }
                if (binding.soloIzq.isChecked){
                    soundPool.setVolume(streamId, 1.0f, 0.0f)
                }
                if (binding.bucle.isChecked){
                    soundPool.setLoop(streamId, -1)
                }
            }


        }

        binding.himno.setOnClickListener{

            soundPool.stop(streamId)
            soundId = soundPool.load(this, R.raw.himno, 1)

            soundPool.setOnLoadCompleteListener{_, _, status ->
                if (status == 0){
                    streamId = soundPool.play(soundId, 1.0f, 1.0f, 1, 0, valorSpinner())
                }
                if (binding.soloDch.isChecked){
                    soundPool.setVolume(streamId, 0.0f, 1.0f)
                }
                if (binding.soloIzq.isChecked){
                    soundPool.setVolume(streamId, 1.0f, 0.0f)
                }
                if (binding.bucle.isChecked){
                    soundPool.setLoop(streamId, -1)
                }
            }


        }

        binding.random.setOnClickListener{
            soundPool.stop(streamId)
            var random = kotlin.random.Random.nextInt(1,4)
            if (random == 1){
                soundId = soundPool.load(this, R.raw.himno, 1)
            }else if(random == 2){
                soundId = soundPool.load(this, R.raw.mivecinojose, 1)
            }else{
                soundId = soundPool.load(this, R.raw.linganguliguli, 1)
            }
            soundPool.setOnLoadCompleteListener{_, _, status ->
                if (status == 0){
                    streamId = soundPool.play(soundId, 1.0f, 1.0f, 1, 0, valorSpinner())
                }
                if (binding.soloDch.isChecked){
                    soundPool.setVolume(streamId, 0.0f, 1.0f)
                }
                if (binding.soloIzq.isChecked){
                    soundPool.setVolume(streamId, 1.0f, 0.0f)
                }
                if (binding.bucle.isChecked){
                    soundPool.setLoop(streamId, -1)
                }
            }

        }


    }

    private fun valorSpinner(): Float {

        if (binding.spinner3.selectedItem.toString() == "x1"){
            return 1.0f

        }else if(binding.spinner3.selectedItem.toString() == "x2"){
            return 2.0f

        }else if(binding.spinner3.selectedItem.toString() == "x1.5"){
            return 1.5f

        }else if(binding.spinner3.selectedItem.toString() == "x0.5"){
            return 0.5f

        }else {
            return 0.25f

        }

    }

}