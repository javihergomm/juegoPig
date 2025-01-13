package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.ActivityRegistroBinding
import kotlinx.coroutines.launch
import java.util.Calendar

class Registro : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroBinding
    private val userDao by lazy { BaseDatos.getInstance(this).userDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerButton.setOnClickListener {
            validateAndRegister()
        }
        binding.botonOculto.isClickable = true
        binding.botonOculto.setOnClickListener {
            val intent = Intent(this, BorrarUsuarios::class.java)
            startActivity(intent)
        }
    }

    private fun validateAndRegister() {
        val username = binding.usernameEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val acceptedTerms = binding.termsCheckBox.isChecked
        val birthDateInMillis = getDateFromDatePicker()

        val errors = mutableListOf<String>()

        if (username.length !in 4..10) {
            errors.add("El Username debe tener entre 4 y 10 caracteres.")
        }

        if (!isPasswordValid(password)) {
            errors.add("El Password debe tener entre 4 y 10 caracteres y al menos un número.")
        }

        if (!isOldEnough(birthDateInMillis)) {
            errors.add("El usuario debe tener al menos 16 años.")
        }

        if (!acceptedTerms) {
            errors.add("Debe aceptar las condiciones de tratamiento de datos.")
        }

        if (errors.isNotEmpty()) {
            Toast.makeText(this, errors.joinToString("\n"), Toast.LENGTH_LONG).show()
            return
        }

        lifecycleScope.launch {
            val userExists = userDao.checkUsernameExists(username) != null
            if (userExists) {
                Toast.makeText(this@Registro, "El Username ya está en uso.", Toast.LENGTH_SHORT).show()
            } else {
                val user = User(username, password, birthDateInMillis, acceptedTerms)
                userDao.insertUser(user)
                Toast.makeText(this@Registro, "Usuario registrado con éxito.", Toast.LENGTH_SHORT).show()
                finish() // Volver a la pantalla anterior
            }
        }
    }

    private fun getDateFromDatePicker(): Long {
        val day = binding.datePicker.dayOfMonth
        val month = binding.datePicker.month
        val year = binding.datePicker.year
        val calendar = Calendar.getInstance().apply {
            set(year, month, day)
        }
        return calendar.timeInMillis
    }

    private fun isPasswordValid(password: String): Boolean {
        val regex = Regex("^(?=.*[0-9]).{4,10}$") // Al menos un número y entre 4 y 10 caracteres
        return regex.matches(password)
    }

    private fun isOldEnough(birthDateInMillis: Long): Boolean {
        val today = Calendar.getInstance()
        val birthDate = Calendar.getInstance().apply {
            timeInMillis = birthDateInMillis
        }
        val age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR)
        if (today.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR)) {
            return age > 16
        }
        return age >= 16
    }
}