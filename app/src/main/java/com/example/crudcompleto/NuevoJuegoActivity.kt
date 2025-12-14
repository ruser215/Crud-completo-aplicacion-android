package com.example.crudcompleto

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.crudcompleto.daoclass.Juego
import com.example.crudcompleto.databinding.ActivityNuevoJuegoBinding

class NuevoJuegoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNuevoJuegoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNuevoJuegoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGuardar.setOnClickListener {
            guardarNuevoJuego()
        }
    }

    private fun guardarNuevoJuego() {
        // Recuperar los datos del formulario
        val titulo = binding.tietTitulo.text.toString()
        val desarrolladora = binding.tietDesarrolladora.text.toString()
        val plataforma = binding.tietPlataforma.text.toString()
        val precioStr = binding.tietPrecio.text.toString()
        val imagenUrl = binding.tietImagen.text.toString()

        // Validar que los campos no estén vacíos
        if (titulo.isEmpty() || desarrolladora.isEmpty() || plataforma.isEmpty() || precioStr.isEmpty() || imagenUrl.isEmpty()) {
            Toast.makeText(this, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // Crear el objeto Juego
        val nuevoJuego = Juego(
            titulo = titulo,
            desarrolladora = desarrolladora,
            plataforma = plataforma,
            precio = precioStr.toDouble(),
            imagen = imagenUrl
        )

        // Devolver el nuevo juego a MainActivity
        val intent = Intent()
        intent.putExtra("nuevo_juego", nuevoJuego) // Asegúrate de que Juego sea Parcelable o Serializable
        setResult(Activity.RESULT_OK, intent)
        finish() // Cerrar esta actividad
    }
}
