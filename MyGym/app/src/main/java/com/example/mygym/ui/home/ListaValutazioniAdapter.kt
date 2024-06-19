package com.example.mygym.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mygym.R
import com.example.mygym.Valutazione
import com.example.mygym.ui.schedaEsercizi.ListaUtentiAdapter

class ListaValutazioniAdapter(val data: List<Valutazione>) :
    RecyclerView.Adapter<ListaValutazioniAdapter.ListaValutazioniViewHolder>() {

        class ListaValutazioniViewHolder(val row: View): RecyclerView.ViewHolder(row){
            val itemTextNomeUtente : TextView = row.findViewById(R.id.itemTextNomeUtente)
            val itemRatingBar : RatingBar = row.findViewById(R.id.itemRatingBar)
            val itemTextGiornata : TextView = row.findViewById(R.id.itemTextGiornata)
            val itemTextRecensione : TextView = row.findViewById(R.id.itemTextRecensione)
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaValutazioniViewHolder {
        val layout = LayoutInflater.from(parent.context).
        inflate(R.layout.item_view_valutazione, parent, false)
        val holder = ListaValutazioniViewHolder(layout)
        return holder
    }

    override fun onBindViewHolder(holder: ListaValutazioniViewHolder, position: Int) {
        holder.itemTextNomeUtente.text = data.get(position).email
        holder.itemTextGiornata.text = data.get(position).giornata.toString()
        holder.itemRatingBar.rating = data.get(position).valutazione
        holder.itemTextRecensione.text = data.get(position).recensione
    }



    override fun getItemCount(): Int {
        return data.size
    }

}
