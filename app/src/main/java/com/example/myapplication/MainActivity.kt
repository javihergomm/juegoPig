package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val userDao by lazy { BaseDatos.getInstance(this).userDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            login()
        }

        binding.registerButton.setOnClickListener {
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }

        binding.usernameEditText.addTextChangedListener { binding.rememberCheckBox.isChecked = false }
        binding.passwordEditText.addTextChangedListener { binding.rememberCheckBox.isChecked = false }
    }

    private fun login() {
        val username = binding.usernameEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            val user = userDao.getUser(username, password)
            if (user != null) {
                val intent = Intent(this@MainActivity, Hub::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this@MainActivity, "Usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show()
            }
        }
    }
}