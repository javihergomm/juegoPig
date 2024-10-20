package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Random

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



    @SuppressLint("MissingInflatedId")
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

        var numJugadores: Int = 0

        boton2Jug.setOnClickListener{
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
            numJugadores = 2

        }
        boton3Jug.setOnClickListener{
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
            numJugadores = 3

        }
        boton4Jug.setOnClickListener{
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
            numJugadores = 4

        }
        var contador: Int = 0
        while (numJugadores > contador){

            contador++
        }

        var resultDado: Int = 0
        var numRonda: Int = 1
        var turno: Int = 1

            botonDado.setOnClickListener{
                resultDado = kotlin.random.Random.nextInt(1,7)
                cara6.visibility = View.GONE
                cara5.visibility = View.GONE
                cara4.visibility = View.GONE
                cara3.visibility = View.GONE
                cara2.visibility = View.GONE
                cara1.visibility = View.GONE

                if (resultDado == 1){
                    cara1.visibility = View.VISIBLE

                    turno++
                }else if (resultDado == 2){
                    cara2.visibility = View.VISIBLE
                }else if (resultDado == 3){
                    cara3.visibility = View.VISIBLE
                }else if (resultDado == 4){
                    cara4.visibility = View.VISIBLE
                }else if (resultDado == 5){
                    cara5.visibility = View.VISIBLE
                }else{
                    cara6.visibility = View.VISIBLE
                }
                if (turno > numJugadores){
                    turno = 1
                    numRonda++
                }

            }





    }
}