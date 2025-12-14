package com.example.crudcompleto

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.crudcompleto.clasepojo.Juego
import com.example.crudcompleto.databinding.ActivityEditarJuegoBinding

class EditarJuego : AppCompatActivity() {
    private lateinit var binding: ActivityEditarJuegoBinding
    private var juegoOriginal: Juego? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarJuegoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // conseguiimos el juego a editar
        juegoOriginal = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("juego_a_editar", Juego::class.java)
        } else {
            intent.getParcelableExtra("juego_a_editar")
        }
        //este erro surge si no se carga el juego
        if (juegoOriginal == null) {
            Toast.makeText(this, "Error al cargar el juego", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        //Esto lo que hace es rellenar los datos del formulario para editarlos
        rellenarDatos(juegoOriginal!!)
        //este es el boton de guardar los cambios
        binding.btnGuardarCambios.setOnClickListener {
            guardarCambios()
        }
    }
    //metodo para guarddar los datos
    private fun rellenarDatos(juego: Juego) {
        binding.tietTitulo.setText(juego.titulo)
        binding.tietDesarrolladora.setText(juego.desarrolladora)
        binding.tietPlataforma.setText(juego.plataforma)
        binding.tietPrecio.setText(juego.precio.toString())
        binding.tietImagen.setText(juego.imagen)
    }
    //metodo para guardar los cambios
    private fun guardarCambios() {
        //extraemos los datos cambioados
        val titulo = binding.tietTitulo.text.toString()
        val desarrolladora = binding.tietDesarrolladora.text.toString()
        val plataforma = binding.tietPlataforma.text.toString()
        val precioStr = binding.tietPrecio.text.toString()
        val imagenUrl = binding.tietImagen.text.toString()
        //si alguno no existe da un error
        if (titulo.isEmpty() || desarrolladora.isEmpty() || plataforma.isEmpty() || precioStr.isEmpty() || imagenUrl.isEmpty()) {
            Toast.makeText(this, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // Creamos el juego editado
        val juegoEditado = juegoOriginal!!.copy(
            titulo = titulo,
            desarrolladora = desarrolladora,
            plataforma = plataforma,
            precio = precioStr.toDouble(),
            imagen = imagenUrl
        )

        // Devolvemos solo el juego editado
        val resultIntent = Intent()
        resultIntent.putExtra("juego_editado", juegoEditado)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}
