package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var boton2Jug: Button
    lateinit var boton3Jug: Button
    lateinit var boton4Jug: Button
    lateinit var textoCuantos: TextView
    lateinit var numRonda: TextView
    lateinit var turnoJugador: TextView
    lateinit var cara1: ImageView
    lateinit var cara2: ImageView
    lateinit var cara3: ImageView
    lateinit var cara4: ImageView
    lateinit var cara5: ImageView
    lateinit var cara6: ImageView
    lateinit var botonDado: Button
    lateinit var textoPuntuacion: TextView
    lateinit var puntuacion: TextView
    lateinit var textoGanador: TextView
    lateinit var textoPuntosJ1: TextView
    lateinit var textoPuntosJ2: TextView
    lateinit var textoPuntosJ3: TextView
    lateinit var textoPuntosJ4: TextView
    lateinit var botonPlantarse: Button



    @SuppressLint("MissingInflatedId", "SuspiciousIndentation", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        boton2Jug = findViewById(R.id.boton2Jugadores)
        boton3Jug = findViewById(R.id.boton3Jugadores)
        boton4Jug = findViewById(R.id.boton4Jugadores)
        textoCuantos = findViewById(R.id.textoCuantos)
        numRonda = findViewById(R.id.numRonda)
        turnoJugador = findViewById(R.id.turnoJugador)
        cara1 = findViewById(R.id.cara1)
        cara2 = findViewById(R.id.cara2)
        cara3 = findViewById(R.id.cara3)
        cara4 = findViewById(R.id.cara4)
        cara5 = findViewById(R.id.cara5)
        cara6 = findViewById(R.id.cara6)
        botonDado = findViewById(R.id.botonDado)
        textoPuntuacion = findViewById(R.id.textoPuntuacion)
        puntuacion = findViewById(R.id.puntuacion)
        textoGanador = findViewById(R.id.textoGanador)
        textoPuntosJ1 = findViewById(R.id.textoPuntosJ1)
        textoPuntosJ2 = findViewById(R.id.textoPuntosJ2)
        textoPuntosJ3 = findViewById(R.id.textoPuntosJ3)
        textoPuntosJ4 = findViewById(R.id.textoPuntosJ4)
        botonPlantarse = findViewById(R.id.botonPlantarse)
        val jugadores = ArrayList<Jugador>()
        var numJugadores: Int = 0

        boton2Jug.setOnClickListener{
            menu()
            numJugadores = 2

            for (i in 1..numJugadores){
                val jugador = Jugador()
                jugador.numeroJugador = i
                jugadores.add(jugador)
            }
            empezarPartida(jugadores, numJugadores)
        }

        boton3Jug.setOnClickListener{
            menu()
            numJugadores = 3

            for (i in 1..numJugadores){
                val jugador = Jugador()
                jugador.numeroJugador = i
                jugadores.add(jugador)
            }
            empezarPartida(jugadores, numJugadores)
        }

        boton4Jug.setOnClickListener{
            menu()
            numJugadores = 4

            for (i in 1..numJugadores){
                val jugador = Jugador()
                jugador.numeroJugador = i
                jugadores.add(jugador)
            }
            empezarPartida(jugadores, numJugadores)
        }
    }

    private fun empezarPartida(
        jugadores: ArrayList<Jugador>,
        numJugadores: Int
    ) {
        var resultDado: Int = 0
        var numRondas: Int = 1
        var turno: Int = 1
        var partidaFinalizada: Boolean = false


            botonDado.setOnClickListener {
                resultDado = Random.nextInt(1, 7)
                cara6.visibility = View.GONE
                cara5.visibility = View.GONE
                cara4.visibility = View.GONE
                cara3.visibility = View.GONE
                cara2.visibility = View.GONE
                cara1.visibility = View.GONE

                if (resultDado == 1) {
                    cara1.visibility = View.VISIBLE
                    jugadores[turno - 1].puntosRonda = 0
                    turno++
                } else if (resultDado == 2) {
                    cara2.visibility = View.VISIBLE
                    jugadores[turno - 1].puntosRonda += 2

                } else if (resultDado == 3) {
                    cara3.visibility = View.VISIBLE
                    jugadores[turno - 1].puntosRonda += 3

                } else if (resultDado == 4) {
                    cara4.visibility = View.VISIBLE
                    jugadores[turno - 1].puntosRonda += 4

                } else if (resultDado == 5) {
                    cara5.visibility = View.VISIBLE
                    jugadores[turno - 1].puntosRonda += 5

                } else {
                    cara6.visibility = View.VISIBLE
                    jugadores[turno - 1].puntosRonda += 6
                }

                marcadores(turno, numJugadores, numRondas, jugadores)

            }


            botonPlantarse.setOnClickListener {
                jugadores[turno - 1].puntos += jugadores[turno - 1].puntosRonda
                turno++
                marcadores(turno, numJugadores, numRondas, jugadores)

            }


    }

    private fun marcadores(
        turno: Int,
        numJugadores: Int,
        numRondas: Int,
        jugadores: ArrayList<Jugador>
    ) {
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
            cara6.visibility = View.GONE
            cara5.visibility = View.GONE
            cara4.visibility = View.GONE
            cara3.visibility = View.GONE
            cara2.visibility = View.GONE
            cara1.visibility = View.GONE
            botonDado.visibility = View.GONE
            puntuacion.visibility = View.GONE
            textoPuntuacion.visibility = View.GONE
            numRonda.visibility = View.GONE
            turnoJugador.visibility = View.GONE
            botonDado.visibility = View.GONE
            textoGanador.visibility = View.VISIBLE
            textoPuntosJ1.visibility = View.VISIBLE
            textoPuntosJ2.visibility = View.VISIBLE
            textoPuntosJ3.visibility = View.VISIBLE
            textoPuntosJ4.visibility = View.VISIBLE
        }
    }

    fun menu() {
        textoCuantos.visibility = View.GONE
        boton3Jug.visibility = View.GONE
        boton4Jug.visibility = View.GONE
        boton2Jug.visibility = View.GONE
        numRonda.visibility = View.VISIBLE
        turnoJugador.visibility = View.VISIBLE
        cara6.visibility = View.VISIBLE
        botonDado.visibility = View.VISIBLE
        puntuacion.visibility = View.VISIBLE
        textoPuntuacion.visibility = View.VISIBLE
        botonPlantarse.visibility = View.VISIBLE
    }
}