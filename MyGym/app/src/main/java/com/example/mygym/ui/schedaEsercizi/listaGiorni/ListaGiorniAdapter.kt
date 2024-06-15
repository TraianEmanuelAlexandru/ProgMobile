package com.example.mygym.ui.schedaEsercizi.listaGiorni

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mygym.R

class ListaGiorniAdapter(val data: List<String>, val emailUtente: String): RecyclerView.Adapter<ListaGiorniAdapter.ListaGiorniViewHolder>() {


    class ListaGiorniViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
        //val itemTextNumeroEsercizi :TextView = row.findViewById(R.id.itemTextNumeroEsercizi)
        val itemTextNumeroGiornata : TextView = row.findViewById(R.id.itemTextNumeroGiornata)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaGiorniViewHolder {
        val layout = LayoutInflater.from(parent.context).
            inflate(R.layout.item_view_giorno, parent, false)
        val holder = ListaGiorniViewHolder(layout)

        holder.row.setOnClickListener{

            val giorno = holder.itemTextNumeroGiornata.text.toString()
            val action = ListaGiorniFragmentDirections.actionFragmentListaGiorniToFragmentListaEsercizi(giorno, emailUtente)
            it.findNavController().navigate(action)
            Toast.makeText(parent.context, "Hai cliccato $giorno ", Toast.LENGTH_SHORT).show()


        }
        return holder
    }

    override fun onBindViewHolder(holder: ListaGiorniViewHolder, position: Int) {
        holder.itemTextNumeroGiornata.text = data.get(position)
    }



    override fun getItemCount(): Int {
        return data.size
    }

}