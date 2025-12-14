package com.example.crudcompleto

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crudcompleto.adapters.aptadorjuego
import com.example.crudcompleto.clasepojo.Juego
import com.example.crudcompleto.databinding.ActivityMainBinding
import com.example.crudcompleto.recursos.juegos

class MainActivity : AppCompatActivity(), aptadorjuego.OnJuegoClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: aptadorjuego

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = aptadorjuego(juegos.listajuegos, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, NuevoJuegoActivity::class.java)
            nuevoJuegoLauncher.launch(intent)
        }
    }

    // onEditClick ahora solo recibe el juego
    override fun onEditClick(juego: Juego) {
        val intent = Intent(this, EditarJuego::class.java)
        intent.putExtra("juego_a_editar", juego)
        editarJuegoLauncher.launch(intent)
    }

    // onDeleteClick se mantiene igual, ya que para borrar la posición del adapter es segura en ese instante
    override fun onDeleteClick(position: Int) {
        if (position >= 0 && position < juegos.listajuegos.size) {
            juegos.listajuegos.removeAt(position)
            adapter.notifyItemRemoved(position)
            Toast.makeText(this, "Juego eliminado", Toast.LENGTH_SHORT).show()
        }
    }

    // metodo para agregar un juego
    private val nuevoJuegoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val nuevoJuego: Juego? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                result.data?.getParcelableExtra("nuevo_juego", Juego::class.java)
            } else {
                result.data?.getParcelableExtra("nuevo_juego")
            }
            if (nuevoJuego != null) {
                juegos.listajuegos.add(0, nuevoJuego)
                adapter.notifyItemInserted(0)
                binding.recyclerView.scrollToPosition(0)
                Toast.makeText(this, "Juego guardado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Metodo para para editar un juego existente
    private val editarJuegoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val juegoEditado: Juego? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                result.data?.getParcelableExtra("juego_editado", Juego::class.java)
            } else {
                result.data?.getParcelableExtra("juego_editado")
            }

            if (juegoEditado != null) {
                // se busca la posición correcta usando el ID del juego
                val index = juegos.listajuegos.indexOfFirst { it.id == juegoEditado.id }
                if (index != -1) {
                    // Si encontramos el juego, lo actualizamos en la posición correcta y segura
                    juegos.listajuegos[index] = juegoEditado
                    adapter.notifyItemChanged(index)
                    Toast.makeText(this, "Cambios guardados", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error: no se encontró el juego a actualizar.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
