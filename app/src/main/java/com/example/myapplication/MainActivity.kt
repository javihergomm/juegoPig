package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    @SuppressLint("MissingInflatedId", "SuspiciousIndentation", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        // Inicializar el binding
        binding = ActivityMainBinding.inflate(layoutInflater)

        // Usar el binding para configurar el contenido de la vista
        setContentView(binding.root)
        val iniciarBoton: Button = binding.IniciarBoton
        val spinner: Spinner = binding.spinner
        val numeros = arrayOf(2, 3, 4)

        // Adaptador para el Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, numeros)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Asigna el adaptador al Spinner
        spinner.adapter = adapter

        val jugadores = ArrayList<Jugador>()
        var numJugadores: Int

        iniciarBoton.setOnClickListener{
            menu()
            numJugadores = binding.spinner.selectedItem.toString().toInt()

            for (i in 1..numJugadores){
                val jugador = Jugador()
                jugador.numeroJugador = i
                jugadores.add(jugador)
            }
            empezarPartida(jugadores, numJugadores)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun empezarPartida(
        jugadores: ArrayList<Jugador>,
        numJugadores: Int
    ) {
        val dado: ImageView = binding.dado
        val botonPlantarse: Button = binding.botonPlantarse
        val botonDado: Button = binding.botonDado

        var numRondas: Int = 1
        var turno: Int = 1
        var tirada: Int = 0


            botonDado.setOnClickListener {
                animarDado(turno, numJugadores, numRondas, jugadores)

            }


            botonPlantarse.setOnClickListener {
                botonPlantarse.visibility = View.GONE
                jugadores[turno - 1].puntos += jugadores[turno - 1].puntosRonda
                turno++
                marcadores(turno, numJugadores, numRondas, jugadores)
                if (turno > numJugadores) {
                    turno = 1
                    numRondas++
                }

            }


    }

    @SuppressLint("SetTextI18n")
    private fun marcadores(
        turno: Int,
        numJugadores: Int,
        numRondas: Int,
        jugadores: ArrayList<Jugador>
    ) {
        val numRonda: TextView = binding.numRonda
        val turnoJugador: TextView = binding.turnoJugador
        val textoPuntuacion: TextView = binding.textoPuntuacion
        val puntuacion: TextView = binding.puntuacion
        val textoGanador: TextView = binding.textoGanador
        val textoPuntosJ1: TextView = binding.textoPuntosJ1
        val textoPuntosJ2: TextView = binding.textoPuntosJ2
        val textoPuntosJ3: TextView = binding.textoPuntosJ3
        val textoPuntosJ4: TextView = binding.textoPuntosJ4
        val botonPlantarse: Button = binding.botonPlantarse
        val botonDado: Button = binding.botonDado
        val dado: ImageView = binding.dado

        var turno1 = turno
        var numRondas1 = numRondas
        if (turno1 > numJugadores) {
            turno1 = 1
            numRondas1++
        }

        puntuacion.setText(jugadores[turno1 - 1].puntosRonda.toString())
        numRonda.setText("Ronda " + numRondas1 + ":")
        turnoJugador.setText("Turno del jugador " + turno1)

        if (numRondas1 > 5) {
            var ganador: Jugador
            dado.visibility = View.GONE
            botonDado.visibility = View.GONE
            puntuacion.visibility = View.GONE
            textoPuntuacion.visibility = View.GONE
            numRonda.visibility = View.GONE
            turnoJugador.visibility = View.GONE
            botonDado.visibility = View.GONE
            textoGanador.visibility = View.VISIBLE
            textoPuntosJ1.setText("Jugador 1: " + jugadores[0].puntos + " puntos")
            textoPuntosJ2.setText("Jugador 2: " + jugadores[1].puntos + " puntos")

            textoPuntosJ1.visibility = View.VISIBLE
            textoPuntosJ2.visibility = View.VISIBLE


            if (numJugadores == 3){
                textoPuntosJ3.setText("Jugador 3: " + jugadores[2].puntos + " puntos")
                textoPuntosJ3.visibility = View.VISIBLE
            }else if (numJugadores == 4){
                textoPuntosJ3.setText("Jugador 3: " + jugadores[2].puntos + " puntos")
                textoPuntosJ4.setText("Jugador 4: " + jugadores[3].puntos + " puntos")
                textoPuntosJ3.visibility = View.VISIBLE
                textoPuntosJ4.visibility = View.VISIBLE
            }

            ganador = jugadores.maxBy { it.puntos }

            textoGanador.text = "¡El ganador es el jugador ${ganador.numeroJugador}!"
            botonPlantarse.visibility =View.GONE
        }
    }

    fun menu() {
        val textoCuantos: TextView = binding.textoCuantos
        val iniciarBoton: Button = binding.IniciarBoton
        val numRonda: TextView = binding.numRonda
        val turnoJugador: TextView = binding.turnoJugador
        val dado: ImageView = binding.dado
        val botonDado: Button = binding.botonDado
        val textoPuntuacion: TextView = binding.textoPuntuacion
        val puntuacion: TextView = binding.puntuacion
        val spinner: Spinner = binding.spinner

        textoCuantos.visibility = View.GONE
        iniciarBoton.visibility = View.GONE
        spinner.visibility = View.GONE
        numRonda.visibility = View.VISIBLE
        turnoJugador.visibility = View.VISIBLE
        dado.visibility = View.VISIBLE
        botonDado.visibility = View.VISIBLE
        puntuacion.visibility = View.VISIBLE
        textoPuntuacion.visibility = View.VISIBLE
    }

    fun animarDado(
        turno: Int,
        numJugadores: Int,
        numRondas: Int,
        jugadores: ArrayList<Jugador>) {

        var turno1 = turno
        var numRondas1 = numRondas

        val textoPuntuacion: TextView = binding.textoPuntuacion
        val puntuacion: TextView = binding.puntuacion
        val botonPlantarse: Button = binding.botonPlantarse
        val botonDado: Button = binding.botonDado
        var currentIndex: Int = 0
        val dado: ImageView = binding.dado
        val imagenesDado = listOf(
            R.drawable.cara1, R.drawable.cara2, R.drawable.cara3,
            R.drawable.cara4, R.drawable.cara5, R.drawable.cara6
        )
        val handler = Handler(Looper.getMainLooper())
        val tiempoTotal = 2000L // 5 segundos en total
        val tiempoRapido = 500L // Los primeros 3 segundos rápidos

        // Frecuencia de cambio durante la fase rápida (cada 100 ms)
        val delayRapido = 100L
        // Frecuencia de cambio durante la fase lenta (cada 500 ms)
        val delayLento = 500L

        // Cambiar rápidamente durante los primeros 3 segundos
        var tiempoTranscurrido = 0L

        puntuacion.visibility = View.GONE
        textoPuntuacion.visibility = View.GONE
        botonDado.visibility = View.GONE
        botonPlantarse.visibility = View.GONE


        val runnable = object : Runnable {
            override fun run() {

                // Cambiar la imagen del dado
                dado.setImageResource(imagenesDado[currentIndex])
                currentIndex = (currentIndex + 1) % imagenesDado.size

                // Determinar si debe cambiar rápido o lento
                if (tiempoTranscurrido < tiempoRapido) {
                    tiempoTranscurrido += delayRapido
                    handler.postDelayed(this, delayRapido)
                } else if (tiempoTranscurrido < tiempoTotal) {
                    tiempoTranscurrido += delayLento
                    handler.postDelayed(this, delayLento)
                } else{
                    puntuacion.visibility = View.VISIBLE
                    textoPuntuacion.visibility = View.VISIBLE
                    botonDado.visibility = View.VISIBLE
                    var tirada: Int = 0
                    tirada++
                    var resultDado: Int = Random.nextInt(1, 7)


                    if (resultDado == 1) {
                        dado.setImageResource(R.drawable.cara1)
                        jugadores[turno - 1].puntosRonda = 0
                        turno1++
                        tirada = 0
                    } else if (resultDado == 2) {
                        dado.setImageResource(R.drawable.cara2)
                        jugadores[turno - 1].puntosRonda += 2

                    } else if (resultDado == 3) {
                        dado.setImageResource(R.drawable.cara3)
                        jugadores[turno - 1].puntosRonda += 3

                    } else if (resultDado == 4) {
                        dado.setImageResource(R.drawable.cara4)
                        jugadores[turno - 1].puntosRonda += 4

                    } else if (resultDado == 5) {
                        dado.setImageResource(R.drawable.cara5)
                        jugadores[turno - 1].puntosRonda += 5

                    } else {
                        dado.setImageResource(R.drawable.cara6)
                        jugadores[turno - 1].puntosRonda += 6
                    }

                    botonPlantarse.visibility = View.VISIBLE

                    marcadores(turno1, numJugadores, numRondas1, jugadores)
                    if (turno > numJugadores) {
                        turno1 = 1
                        numRondas1++
                        tirada = 0
                    }
                    if (tirada == 0){
                        botonPlantarse.visibility = View.GONE
                    }
                }
            }
        }

        // Iniciar la animación
        handler.post(runnable)
    }
}
