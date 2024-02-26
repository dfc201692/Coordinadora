package com.example.proyecto_coordinadora

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class HomeActivity : AppCompatActivity() {

    // Declaración de botón y TextView aquí
    private lateinit var btnHacerSolicitud: Button
    private lateinit var textViewResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        btnHacerSolicitud = findViewById(R.id.btnHacerSolicitud)
        textViewResult = findViewById(R.id.textViewResult)

        btnHacerSolicitud.setOnClickListener {
            // Reemplaza la URL con la dirección correcta de tu API
            val url =
                "https://us-central1-cm-tim-goo-test.cloudfunctions.net/prueba_tecnica/carga_trabajo/equipo/77/terminal/2"

            // Realiza la solicitud en un hilo separado para evitar bloquear el hilo principal
            MyAsyncTask(textViewResult).execute(url)
        }
    }

    private inner class MyAsyncTask(private val textViewResult: TextView) :
        AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String): String {
            return try {
                val url = URL(params[0])
                val urlConnection = url.openConnection() as HttpURLConnection
                try {
                    val bufferedReader =
                        BufferedReader(InputStreamReader(urlConnection.inputStream))
                    val stringBuilder = StringBuilder()
                    var line: String?
                    while (bufferedReader.readLine().also { line = it } != null) {
                        stringBuilder.append(line).append("\n")
                    }
                    bufferedReader.close()
                    stringBuilder.toString()
                } finally {
                    urlConnection.disconnect()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                "Error al realizar la solicitud: ${e.message}"
            }
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            // Maneja la respuesta aquí
            textViewResult.text = result
        }
    }
}
