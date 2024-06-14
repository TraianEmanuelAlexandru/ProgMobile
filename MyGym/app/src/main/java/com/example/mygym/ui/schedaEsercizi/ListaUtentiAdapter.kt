package com.example.mygym.ui.schedaEsercizi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mygym.R

class ListaUtentiAdapter(val data: List<String>): RecyclerView.Adapter<ListaUtentiAdapter.ListaUtentiViewHolder>() {


    class ListaUtentiViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
        val itemTextUtente :TextView = row.findViewById(R.id.itemTextEmailUtente)
        val itemTextNumeroUtente : TextView = row.findViewById(R.id.itemTextNumeroUtente)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaUtentiViewHolder {
        val layout = LayoutInflater.from(parent.context).
            inflate(R.layout.item_view_utente, parent, false)
        val holder = ListaUtentiViewHolder(layout)

        holder.row.setOnClickListener{
            val argomento = holder.itemTextUtente.text.toString()
            val action = ListaUtentiFragmentDirections.actionFragmentListaUtentiToFragmentListaGiorni(argomento)
            it.findNavController().navigate(action)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ListaUtentiViewHolder, position: Int) {
        holder.itemTextUtente.text = data.get(position)
        holder.itemTextNumeroUtente.text = position.toString()
    }



    override fun getItemCount(): Int {
        return data.size
    }

}