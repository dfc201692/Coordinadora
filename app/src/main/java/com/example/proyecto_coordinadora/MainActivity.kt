package com.example.proyecto_coordinadora

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.proyecto_coordinadora.databinding.ActivityMainBinding
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import data.remoto.PersonaViewModel
import data.remoto.PersonaViewModelFactory
import data.repository.PersonaRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: PersonaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = PersonaRepository()
        val viewModelFactory = PersonaViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PersonaViewModel::class.java)

        binding.btnLogin.setOnClickListener {
            val usuario = binding.etUsuario.text.toString()
            val pin = binding.etPin.text.toString()

            viewModel.validarUsuarioYPin(usuario, pin)

            viewModel.resultadoValidacion.observe(this, Observer { resultado ->
                if (resultado) {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                } else {
                    showAlert("Error de autenticación", "Usuario o PIN incorrecto")
                }
            })
        }

    }

    private fun showAlert(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}



