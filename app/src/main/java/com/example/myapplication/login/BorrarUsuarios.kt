package com.example.myapplication.login

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.BaseDatos
import com.example.myapplication.pig.NombresAdapter
import com.example.myapplication.databinding.ActivityBorrarUsuariosBinding
import kotlinx.coroutines.launch

class BorrarUsuarios : AppCompatActivity() {
    private lateinit var binding: ActivityBorrarUsuariosBinding
    private val userDao by lazy { BaseDatos.getInstance(this).userDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        var nombreSeleccionado: String? = null
        super.onCreate(savedInstanceState)
        binding = ActivityBorrarUsuariosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val recyclerView: RecyclerView = binding.recyclerView
        val botonBorrar: Button = binding.botonBorrar
        lifecycleScope.launch {
            val usernames: List<String> = userDao.getAllUsers()
            recyclerView.layoutManager = LinearLayoutManager(this@BorrarUsuarios)
            recyclerView.adapter = NombresAdapter(usernames){ nombre ->
                nombreSeleccionado = nombre
            }

        }
        botonBorrar.setOnClickListener{
            lifecycleScope.launch{
                if (nombreSeleccionado != null){
                    userDao.deleteUser(nombreSeleccionado!!)
                    Toast.makeText(this@BorrarUsuarios, "Usuario borrado con éxito", Toast.LENGTH_SHORT).show()
                    finish()
                }else{
                    Toast.makeText(this@BorrarUsuarios, "Selecciona un usurio para borrarlo", Toast.LENGTH_SHORT).show()
                }
            }

        }


    }

}