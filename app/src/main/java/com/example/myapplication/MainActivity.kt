package com.example.myapplication

import android.content.Context
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
    private val sharedPreferences by lazy { getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cargar datos almacenados si el checkbox estaba marcado
        loadSavedLogin()

        binding.rememberCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) {
                // Si se desmarca, limpiamos los datos de SharedPreferences
                clearSavedLogin()
            }
        }

        binding.usernameEditText.setOnFocusChangeListener { _, _ ->
            binding.rememberCheckBox.isChecked = false
        }

        binding.passwordEditText.setOnFocusChangeListener { _, _ ->
            binding.rememberCheckBox.isChecked = false
        }

        binding.loginButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            lifecycleScope.launch {
                val user = userDao.getUser(username, password)
                if (user != null) {
                    // Guardar datos si el checkbox está marcado
                    if (binding.rememberCheckBox.isChecked) {
                        saveLogin(username, password)
                    }
                    // Ir a la pantalla HUB
                    goToHub()
                } else {
                    Toast.makeText(this@MainActivity, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.registerButton.setOnClickListener {
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }
    }

    private fun saveLogin(username: String, password: String) {
        with(sharedPreferences.edit()) {
            putBoolean("remember", true)
            putString("username", username)
            putString("password", password)
            apply()
        }
    }

    private fun loadSavedLogin() {
        val remember = sharedPreferences.getBoolean("remember", false)
        if (remember) {
            val username = sharedPreferences.getString("username", "")
            val password = sharedPreferences.getString("password", "")
            binding.usernameEditText.setText(username)
            binding.passwordEditText.setText(password)
            binding.rememberCheckBox.isChecked = true
        }
    }

    private fun clearSavedLogin() {
        with(sharedPreferences.edit()) {
            remove("remember")
            remove("username")
            remove("password")
            apply()
        }
    }

    private fun goToHub() {
        val intent = Intent(this@MainActivity, Hub::class.java)
        startActivity(intent)
    }
}