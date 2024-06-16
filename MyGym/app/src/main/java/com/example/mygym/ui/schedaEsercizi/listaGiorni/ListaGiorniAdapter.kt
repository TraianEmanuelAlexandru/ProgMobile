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
import com.example.mygym.R
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.NonDisposableHandle.parent

class ListaGiorniAdapter(val data: List<String>, val emailUtente: String, var fromUser: Boolean = false): RecyclerView.Adapter<ListaGiorniAdapter.ListaGiorniViewHolder>() {
    private lateinit var firestore : FirebaseFirestore
    private lateinit var dbRef: CollectionReference


    class ListaGiorniViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
        val itemTextNumeroEsercizi :TextView = row.findViewById(R.id.itemTextNumeroEsercizi)
        val itemTextNumeroGiornata : TextView = row.findViewById(R.id.itemTextNumeroGiornata)
        val itemButtonModificaGiornata : ImageButton = row.findViewById(R.id.itemButtonModificaGiornata)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaGiorniViewHolder {
        val layout = LayoutInflater.from(parent.context).
            inflate(R.layout.item_view_giorno, parent, false)
        val holder = ListaGiorniViewHolder(layout)
        firestore = FirebaseFirestore.getInstance()
        dbRef = firestore.collection("Utenti").document(emailUtente).collection("EserciziPerGiorno")

        holder.row.setOnClickListener{

            val giorno = data.get(holder.adapterPosition)
            if (!fromUser) {
                val action =
                    ListaGiorniFragmentDirections.actionFragmentListaGiorniToFragmentListaEsercizi(
                        emailUtente,
                        giorno
                    )
                it.findNavController().navigate(action)
            }else{
                val action =
                    ListaGiorniUtenteFragmentDirections.actionFragmentListaGiorniUtenteToFragmentListaEsercizi(
                        emailUtente,
                        giorno
                    )
                it.findNavController().navigate(action)
            }
        }


        holder.itemButtonModificaGiornata.setOnClickListener{
            if (fromUser) {
                val popup = PopupMenu(parent.context, holder.itemButtonModificaGiornata)
                popup.inflate(R.menu.popup_menu_giorno_utente)

                popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

                    when (item!!.itemId) {
                        R.id.header1 -> {
                            val builder = AlertDialog.Builder(parent.context)
                            val inflater = LayoutInflater.from(parent.context)
                            builder.setTitle("Valuta Giornata Allenamento")
                            val dialogLayout = inflater.inflate(R.layout.alert_valutazione_giornata, null)
                            val recensione  = dialogLayout.findViewById<EditText>(R.id.editTextRecensioneGiornata)
                            val ratingBar  = dialogLayout.findViewById<RatingBar>(R.id.ratingBarGiornata)
                            builder.setView(dialogLayout)
                            builder.setPositiveButton("OK") { dialogInterface, i ->
                                val rec = recensione.text.toString()
                                Toast.makeText(parent.context, "La ringraziamo per la sua recensione! " , Toast.LENGTH_SHORT).show()
                                val valutazione = hashMapOf(
                                    "recensione" to rec,
                                    "rating" to ratingBar.rating,
                                    "utente" to emailUtente,
                                    "giornata" to holder.adapterPosition
                                )
                                firestore.collection("ValutazioniGiornate").add(valutazione)
                            }
                            builder.show()

                        }
                    }

                    true
                })

                popup.show()
            }else{
                val popup = PopupMenu(parent.context, holder.itemButtonModificaGiornata)
                popup.inflate(R.menu.popup_menu_giorno_utente)

                popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

                    when (item!!.itemId) {
                        R.id.header1 -> {
                            dbRef.document(data.get(holder.adapterPosition)).delete()
                        }
                    }

                    true
                })

                popup.show()
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: ListaGiorniViewHolder, position: Int) {
        val numGiorno = position+1
        holder.itemTextNumeroGiornata.text = numGiorno.toString()
        dbRef.document(data.get(position)).get().addOnSuccessListener {
                document->
            holder.itemTextNumeroEsercizi.text =  document.data!!.size.toString()
        }

    }



    override fun getItemCount(): Int {
        return data.size
    }

}