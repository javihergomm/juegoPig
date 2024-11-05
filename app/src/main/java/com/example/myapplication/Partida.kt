package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityEleccionNombreBinding
import com.example.myapplication.databinding.ActivityPartidaBinding
import kotlin.random.Random

class Partida : AppCompatActivity() {
    private lateinit var binding: ActivityPartidaBinding
    var turno: Int = 1
    var numRondas: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_partida)

        binding = ActivityPartidaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val numJugadores = intent.getIntExtra("NUM_JUGADORES", 0)
        val nombres = listOf(intent.getStringExtra("NOMBRE_JUG1"),
            intent.getStringExtra("NOMBRE_JUG2"),
            intent.getStringExtra("NOMBRE_JUG3"),
            intent.getStringExtra("NOMBRE_JUG4"))

        val jugadores = ArrayList<Jugador>()

        val nombresAlAzar = nombres.shuffled()
        for (i in 1..numJugadores) {
            val jugador = Jugador()
            jugador.numeroJugador = i
            jugador.nombreJugador = nombresAlAzar[i-1].toString()
            jugadores.add(jugador)
        }

        menu(jugadores[0].nombreJugador)
        empezarPartida(jugadores,numJugadores)

    }

        @SuppressLint("SuspiciousIndentation")
        private fun empezarPartida(
            jugadores: ArrayList<Jugador>,
            numJugadores: Int
        ) {
            val dado: ImageView = binding.dado2
            val botonPlantarse: Button = binding.botonPlantarse2
            val botonDado: Button = binding.botonDado2



            botonDado.setOnClickListener {
                animarDado(numJugadores, jugadores)

            }


            botonPlantarse.setOnClickListener {
                botonPlantarse.visibility = View.GONE
                jugadores[turno - 1].puntos += jugadores[turno - 1].puntosRonda
                turno++
                marcadores(numJugadores, jugadores)
                if (turno > numJugadores) {
                    turno = 1
                    numRondas++
                }

            }


        }

        @SuppressLint("SetTextI18n")
        private fun marcadores(
            numJugadores: Int,
            jugadores: ArrayList<Jugador>
        ) {
            val numRonda: TextView = binding.numRonda2
            val turnoJugador: TextView = binding.turnoJugador2
            val textoPuntuacion: TextView = binding.textoPuntuacion2
            val puntuacion: TextView = binding.puntuacion2
            val textoGanador: TextView = binding.textoGanador2
            val textoPuntosJ1: TextView = binding.textoPuntosJ1
            val textoPuntosJ2: TextView = binding.textoPuntosJ2
            val textoPuntosJ3: TextView = binding.textoPuntosJ3
            val textoPuntosJ4: TextView = binding.textoPuntosJ4
            val botonPlantarse: Button = binding.botonPlantarse2
            val botonDado: Button = binding.botonDado2
            val dado: ImageView = binding.dado2
            if (turno > numJugadores) {
                turno = 1
                numRondas++
            }

            puntuacion.setText(jugadores[turno - 1].puntosRonda.toString())
            numRonda.setText("Ronda " + numRondas + ":")
            turnoJugador.setText("Turno de " + jugadores[turno-1].nombreJugador)

            if (numRondas > 5) {
                dado.visibility = View.GONE
                botonDado.visibility = View.GONE
                puntuacion.visibility = View.GONE
                textoPuntuacion.visibility = View.GONE
                numRonda.visibility = View.GONE
                turnoJugador.visibility = View.GONE
                botonDado.visibility = View.GONE
                textoGanador.visibility = View.VISIBLE
                textoPuntosJ1.setText(jugadores[0].nombreJugador + " : " + jugadores[0].puntos + " puntos")
                textoPuntosJ2.setText(jugadores[1].nombreJugador + " : " + jugadores[1].puntos + " puntos")

                textoPuntosJ1.visibility = View.VISIBLE
                textoPuntosJ2.visibility = View.VISIBLE


                if (numJugadores == 3){
                    textoPuntosJ3.setText( jugadores[2].nombreJugador + " : " + jugadores[2].puntos + " puntos")
                    textoPuntosJ3.visibility = View.VISIBLE
                }else if (numJugadores == 4){
                    textoPuntosJ3.setText( jugadores[2].nombreJugador + " : " + jugadores[2].puntos + " puntos")
                    textoPuntosJ4.setText(jugadores[3].nombreJugador + " : " + jugadores[3].puntos + " puntos")
                    textoPuntosJ3.visibility = View.VISIBLE
                    textoPuntosJ4.visibility = View.VISIBLE
                }

                val jugadoresMaxPuntos = jugadorConMasPuntos(jugadores)

                if(jugadoresMaxPuntos.size == 1){
                    textoGanador.text = "¡El ganador es ${jugadoresMaxPuntos[0].nombreJugador}!"

                } else {
                    textoGanador.text = "¡Nadie gana, ha habido un empate!"
                }

                botonPlantarse.visibility =View.GONE
            }
        }


        fun menu(
            nombre1: String
        ) {

            val numRonda: TextView = binding.numRonda2
            val turnoJugador: TextView = binding.turnoJugador2
            val dado: ImageView = binding.dado2
            val botonDado: Button = binding.botonDado2
            val textoPuntuacion: TextView = binding.textoPuntuacion2
            val puntuacion: TextView = binding.puntuacion2


            numRonda.visibility = View.VISIBLE
            turnoJugador.visibility = View.VISIBLE
            dado.visibility = View.VISIBLE
            botonDado.visibility = View.VISIBLE
            puntuacion.visibility = View.VISIBLE
            textoPuntuacion.visibility = View.VISIBLE

            turnoJugador.setText("Turno de $nombre1")
        }

        fun animarDado(
            numJugadores: Int,
            jugadores: ArrayList<Jugador>) {

            val textoPuntuacion: TextView = binding.textoPuntuacion2
            val puntuacion: TextView = binding.puntuacion2
            val botonPlantarse: Button = binding.botonPlantarse2
            val botonDado: Button = binding.botonDado2
            var currentIndex: Int = 0
            val dado: ImageView = binding.dado2
            val imagenesDado = listOf(
                R.drawable.cara1, R.drawable.cara2, R.drawable.cara3,
                R.drawable.cara4, R.drawable.cara5, R.drawable.cara6
            )
            val handler = Handler(Looper.getMainLooper())
            val tiempoTotal = 2000L
            val tiempoRapido = 1000L

            val delayRapido = 100L

            val delayLento = 400L


            var tiempoTranscurrido = 0L

            puntuacion.visibility = View.GONE
            textoPuntuacion.visibility = View.GONE
            botonDado.visibility = View.GONE
            botonPlantarse.visibility = View.GONE


            val runnable = object : Runnable {
                override fun run() {

                    dado.setImageResource(imagenesDado[currentIndex])
                    currentIndex = (currentIndex + 1) % imagenesDado.size

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
                            turno++
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

                        marcadores(numJugadores, jugadores)
                        if (turno > numJugadores) {
                            turno = 1
                            numRondas++
                            tirada = 0
                        }
                        if (tirada == 0){
                            botonPlantarse.visibility = View.GONE
                        }
                    }
                }
            }

            handler.post(runnable)
        }

        fun jugadorConMasPuntos(jugadores: ArrayList<Jugador>): List<Jugador> {
            val maxPuntos = jugadores.maxOf { it.puntos }

            val jugadoresConMasPuntos = jugadores.filter { it.puntos == maxPuntos }

            return jugadoresConMasPuntos
        }



}