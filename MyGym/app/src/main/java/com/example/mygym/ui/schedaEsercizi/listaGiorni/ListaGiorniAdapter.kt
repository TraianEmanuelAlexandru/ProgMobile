package com.example.mygym.ui.schedaEsercizi.listaGiorni

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mygym.R
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class ListaGiorniAdapter(val data: List<String>, val emailUtente: String): RecyclerView.Adapter<ListaGiorniAdapter.ListaGiorniViewHolder>() {
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

            val giorno = holder.itemTextNumeroGiornata.text.toString()
            val action = ListaGiorniFragmentDirections.actionFragmentListaGiorniToFragmentListaEsercizi(emailUtente, giorno)
            it.findNavController().navigate(action)
            Toast.makeText(parent.context, "Hai cliccato $giorno ", Toast.LENGTH_SHORT).show()


        }

        holder.itemButtonModificaGiornata.setOnClickListener{

        }
        return holder
    }

    override fun onBindViewHolder(holder: ListaGiorniViewHolder, position: Int) {
        holder.itemTextNumeroGiornata.text = data.get(position)
        dbRef.document(data.get(position)).get().addOnSuccessListener {
                document->
            holder.itemTextNumeroEsercizi.text =  document.data!!.size.toString()
        }

    }



    override fun getItemCount(): Int {
        return data.size
    }

}