package com.example.crudcompleto.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.crudcompleto.R
import com.example.crudcompleto.daoclass.Juego
import com.example.crudcompleto.databinding.ItemJuegoBinding

class aptadorjuego(
    private val listajuegos: MutableList<Juego>,
    private val listener: OnJuegoClickListener
) : RecyclerView.Adapter<aptadorjuego.JuegoViewHolder>() {

    interface OnJuegoClickListener {
        fun onEditClick(juego: Juego) // Ya no necesitamos la posición aquí
        fun onDeleteClick(position: Int)
    }
    //esto muestra donde debe ir cada dato
    class JuegoViewHolder(val binding: ItemJuegoBinding) : RecyclerView.ViewHolder(binding.root) {
        val titulo = binding.txtTitulo
        val imagen = binding.igmPortada
        val desarrolladora = binding.textDesarrolladora
        val plataforma = binding.textPlataforma
        val precio = binding.txtPrecio
        val btnDelete = binding.btnDelete
        val btnEdit = binding.btnEdit
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JuegoViewHolder {
        val binding = ItemJuegoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return JuegoViewHolder(binding)
    }
    //esto lo que hace es rellenar los datos en el item
    override fun onBindViewHolder(holder: JuegoViewHolder, position: Int) {
            val juego = listajuegos[position]

            holder.titulo.text = juego.titulo
            holder.desarrolladora.text = juego.desarrolladora
            holder.precio.text = "${juego.precio}€"
            holder.plataforma.text = juego.plataforma
            //esto es para poder cargar las imagenes
            Glide.with(holder.itemView.context)
                    //cargamos la url pasada del objeto
                .load(juego.imagen)
                    //esta es la imagen por defecto
                .placeholder(R.drawable.ic_launcher_background)
                    //esta es la imagen en caso de error
                .error(com.google.android.material.R.drawable.mtrl_ic_error)
                    //esto es ya para poner la imagen en su sitio
                .into(holder.imagen)
                //Este el es boton para editar de cada juego
            holder.btnEdit.setOnClickListener {
                listener.onEditClick(juego) // Solo pasamos el juego
            }
            //este es el boton para eliminar de cada juego
            holder.btnDelete.setOnClickListener {
                // Captura la posicion para borrar el juego
                val currentPosition = holder.adapterPosition
                if (currentPosition != RecyclerView.NO_POSITION) {
                    //esto ya borra el juego
                    listener.onDeleteClick(currentPosition)
                }
            }
    }

    override fun getItemCount(): Int {
        return listajuegos.size
    }
}