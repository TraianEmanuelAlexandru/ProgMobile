package com.example.mygym.ui.schedaEsercizi.listaGiorni

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mygym.EsercizioRoomDatabase
import com.example.mygym.Giorno
import com.example.mygym.R
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.NonDisposableHandle.parent

class ListaGiorniUtenteAdapter(val data: List<Giorno>): RecyclerView.Adapter<ListaGiorniUtenteAdapter.ListaGiorniViewHolder>() {

    class ListaGiorniViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
        val itemTextNumeroGiornata : TextView = row.findViewById(R.id.itemTextNumeroGiornata)
        val itemButtonModificaGiornata : ImageButton = row.findViewById(R.id.itemButtonModificaGiornata)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaGiorniViewHolder {
        val layout = LayoutInflater.from(parent.context).
            inflate(R.layout.item_view_giorno, parent, false)
        val holder = ListaGiorniViewHolder(layout)
        val giornoDao = EsercizioRoomDatabase.getInstance(parent.context).giornoDao()

        holder.row.setOnClickListener{

            val giorno = data.get(holder.adapterPosition)
            val action =
                ListaGiorniUtenteFragmentDirections.actionFragmentListaGiorniUtenteToFragmentListaEserciziUtente(giorno.numeroGiorno)
            it.findNavController().navigate(action)
        }
        holder.itemButtonModificaGiornata.setOnClickListener{
            val popup = PopupMenu(parent.context, holder.itemButtonModificaGiornata)
            popup.inflate(R.menu.popup_menu_giorno_utente_personale)

            popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->
                when (item!!.itemId) {
                    R.id.header1 -> {
                        giornoDao.deleteEsercizio(data.get(holder.adapterPosition))
                    }
                }

                true
            })

            popup.show()

        }
        return holder
    }

    override fun onBindViewHolder(holder: ListaGiorniViewHolder, position: Int) {
        val numGiorno = position+1
        holder.itemTextNumeroGiornata.text = numGiorno.toString()

    }



    override fun getItemCount(): Int {
        return data.size
    }

}