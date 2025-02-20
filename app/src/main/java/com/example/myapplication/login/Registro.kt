package com.example.myapplication.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.BaseDatos
import com.example.myapplication.User
import com.example.myapplication.databinding.ActivityRegistroBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Calendar

class Registro : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroBinding
    private val userDao by lazy { BaseDatos.getInstance(this).userDao() }

    private lateinit var image1: String
    private lateinit var image2: String
    private lateinit var image3: String
    private lateinit var imageSelected:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var genero: String =""


        binding.hombre.setOnClickListener {

            binding.textView3.visibility = View.GONE
            binding.hombre.visibility = View.GONE
            binding.mujer.visibility = View.GONE
            binding.imageButton4.visibility = View.VISIBLE
            binding.imageButton5.visibility = View.VISIBLE
            binding.imageButton6.visibility = View.VISIBLE
            binding.cambiarImagenes.visibility = View.VISIBLE
            binding.textView5.visibility = View.VISIBLE

            searchByGender("male", binding.imageButton4)
            searchByGender("male", binding.imageButton5)
            searchByGender("male", binding.imageButton6)

            genero="male"


        }
        binding.mujer.setOnClickListener {

            binding.textView3.visibility = View.GONE
            binding.hombre.visibility = View.GONE
            binding.mujer.visibility = View.GONE
            binding.imageButton4.visibility = View.VISIBLE
            binding.imageButton5.visibility = View.VISIBLE
            binding.imageButton6.visibility = View.VISIBLE
            binding.cambiarImagenes.visibility = View.VISIBLE
            binding.textView5.visibility = View.VISIBLE


            searchByGender("female", binding.imageButton4)
            searchByGender("female", binding.imageButton5)
            searchByGender("female", binding.imageButton6)

            genero="female"

        }

        binding.cambiarImagenes.setOnClickListener{

            searchByGender(genero, binding.imageButton4)
            searchByGender(genero, binding.imageButton5)
            searchByGender(genero, binding.imageButton6)

        }

        binding.imageButton4.setOnClickListener{

            imageSelected = image1
            menuRegistro()
        }

        binding.imageButton5.setOnClickListener{

            imageSelected = image2

            menuRegistro()
        }

        binding.imageButton6.setOnClickListener {

            imageSelected = image3
            menuRegistro()
        }

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
                val user = User(username, password, birthDateInMillis, acceptedTerms, imageSelected)
                userDao.insertUser(user)
                Toast.makeText(this@Registro, "Usuario registrado con éxito.", Toast.LENGTH_SHORT).show()
                finish()
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
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://randomuser.me/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private fun searchByGender(query:String, image:ImageButton){
        var imageUrl: String? = ""
        CoroutineScope(Dispatchers.IO).launch {
            val service = getRetrofit().create(RandomUserService::class.java)
            val call = service.getPictureByGender("?gender=$query&inc=picture")
            val response = call.execute()
            runOnUiThread{
                if (response.isSuccessful){
                    imageUrl = response.body()?.results?.get(0)?.picture?.large

                    when (image) {
                        binding.imageButton4 -> image1 = imageUrl ?: ""
                        binding.imageButton5 -> image2 = imageUrl ?: ""
                        binding.imageButton6 -> image3 = imageUrl ?: ""
                    }


                    Picasso.get().load(imageUrl).into(image)
                }
            }
        }

    }
    private fun menuRegistro(){
        binding.registerButton.visibility = View.VISIBLE
        binding.datePicker.visibility = View.VISIBLE
        binding.passwordEditText.visibility = View.VISIBLE
        binding.usernameEditText.visibility = View.VISIBLE
        binding.TextViewFechaNac.visibility = View.VISIBLE
        binding.termsCheckBox.visibility = View.VISIBLE
        binding.imageButton5.visibility = View.GONE
        binding.imageButton4.visibility = View.GONE
        binding.imageButton6.visibility = View.GONE
        binding.cambiarImagenes.visibility = View.GONE
        binding.textView5.visibility = View.GONE
    }
}