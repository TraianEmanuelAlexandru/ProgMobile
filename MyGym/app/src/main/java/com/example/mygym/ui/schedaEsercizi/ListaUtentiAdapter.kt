package com.example.mygym.ui.schedaEsercizi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavArgs
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.example.mygym.R
import com.example.mygym.ui.schedaEsercizi.ListaUtentiFragmentDirections

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
            val action = ListaUtentiFragmentDirections.actionFragmentListaUtentiToFragmentListaEsercizi(argomento)
            it.findNavController().navigate(action)
            //it?.findNavController()!!.navigate(R.id.fragment_scheda_esercizi, navigatorExtras = holder.itemTextUtente)
            Toast.makeText(parent.context, "Hai cliccato ", Toast.LENGTH_SHORT).show()
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