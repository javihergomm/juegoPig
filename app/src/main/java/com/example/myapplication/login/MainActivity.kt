package com.example.myapplication.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.BaseDatos
import com.example.myapplication.Hub
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

        loadSavedLogin()

        binding.rememberCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) {
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
            val irrelevante = binding.Irrelevante.text.toString()

            lifecycleScope.launch {
                val user = userDao.getUser(username, password)
                if (user != null && irrelevante != "") {
                    if (binding.rememberCheckBox.isChecked) {
                        saveLogin(username, password, irrelevante)
                    }
                    goToHub()
                } else if(username == "" || password == "" || irrelevante == ""){
                    Toast.makeText(this@MainActivity, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
                } else{
                    Toast.makeText(this@MainActivity, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.registerButton.setOnClickListener {
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }
    }

    private fun saveLogin(username: String, password: String, irrelevante: String) {
        with(sharedPreferences.edit()) {
            putBoolean("remember", true)
            putString("username", username)
            putString("password", password)
            putString("irrelevante", irrelevante)
            apply()
        }
    }

    private fun loadSavedLogin() {
        val remember = sharedPreferences.getBoolean("remember", false)
        if (remember) {
            val username = sharedPreferences.getString("username", "")
            val password = sharedPreferences.getString("password", "")
            val irrelevante = sharedPreferences.getString("irrelevante", "")
            binding.usernameEditText.setText(username)
            binding.passwordEditText.setText(password)
            binding.Irrelevante.setText(irrelevante)
            binding.rememberCheckBox.isChecked = true
        }
    }

    private fun clearSavedLogin() {
        with(sharedPreferences.edit()) {
            remove("remember")
            remove("username")
            remove("password")
            remove("irrelevante")
            apply()
        }
    }

    private fun goToHub() {
        val intent = Intent(this@MainActivity, Hub::class.java)
        intent.putExtra("USUARIO", binding.usernameEditText.text.toString())
        startActivity(intent)
    }
}