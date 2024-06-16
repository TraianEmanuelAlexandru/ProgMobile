package com.example.mygym.ui.schedaEsercizi.listaGiorni.listaEsercizi

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mygym.EsercizioPerUtente
import com.example.mygym.R
import com.google.firebase.firestore.FirebaseFirestore

class ListaEserciziAdapter(val data: List<EsercizioPerUtente>): RecyclerView.Adapter<ListaEserciziAdapter.ListaEserciziViewHolder>() {

    //private val textRimuovi = "Rimuovere?"

    class ListaEserciziViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
        val itemTextNomeEsercizio :TextView = row.findViewById(R.id.itemTextNomeEsercizio)
        val itemTextBodyPart : TextView = row.findViewById(R.id.itemTextBodyPart)
        val itemTextTarget : TextView = row.findViewById(R.id.itemTextTarget)
        val webView : WebView = row.findViewById(R.id.webView)
        val itemDefinitiveTextInstructions : TextView = row.findViewById(R.id.itemDefinitiveTextInstructions)
        val itemTextInstructions : TextView = row.findViewById(R.id.itemTextInstructions)
        val itemDefinitiveTextEquipment : TextView = row.findViewById(R.id.itemDefinitiveTextEquipment)
        val itemTextEquipment : TextView = row.findViewById(R.id.itemTextEquipment)
        val itemDefinitiveTextSecondaryMuscles : TextView = row.findViewById(R.id.itemDefinitiveTextSecondaryMuscles)
        val itemTextSecondaryMuscles : TextView = row.findViewById(R.id.itemTextSecondaryMuscles)
        val itemEditTextSerieNumber : TextView = row.findViewById(R.id.itemEditTextSerieNumber)
        val itemEditTextRepNumber : TextView = row.findViewById(R.id.itemEditTextRepNumber)
        val itemEditTextKgNumber : TextView = row.findViewById(R.id.itemEditTextKgNumber)
        val itemEditRatingBar : RatingBar = row.findViewById(R.id.itemEditRatingBar)
        //val itemButtonRimuoviEsercizio : Button = row.findViewById(R.id.itemButtonAggiungiEsercizio)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaEserciziViewHolder {
        val layout = LayoutInflater.from(parent.context).
            inflate(R.layout.item_user_view_esercizio, parent, false)
        val holder = ListaEserciziViewHolder(layout)
        var click = false
        holder.webView.webViewClient
        holder.webView.settings.useWideViewPort = true
        holder.webView.settings.loadWithOverviewMode = true

        holder.webView.setOnLongClickListener{
            if (click == true){
                holder.itemDefinitiveTextInstructions.visibility = View.GONE
                holder.itemTextInstructions.visibility = View.GONE
                holder.itemDefinitiveTextEquipment.visibility = View.GONE
                holder.itemTextEquipment.visibility = View.GONE
                holder.itemDefinitiveTextSecondaryMuscles.visibility = View.GONE
                holder.itemTextSecondaryMuscles.visibility = View.GONE
                click = false
            }else {
                holder.itemDefinitiveTextInstructions.visibility = View.VISIBLE
                holder.itemTextInstructions.visibility = View.VISIBLE
                holder.itemDefinitiveTextEquipment.visibility = View.VISIBLE
                holder.itemTextEquipment.visibility = View.VISIBLE
                holder.itemDefinitiveTextSecondaryMuscles.visibility = View.VISIBLE
                holder.itemTextSecondaryMuscles.visibility = View.VISIBLE
                click = true

            }
            true
        }

        holder.itemEditRatingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            val firestore = FirebaseFirestore.getInstance()
            val valutazioneEsercizio = hashMapOf(
                "valutazione" to rating,
                "esercizio" to data.get(holder.adapterPosition)
            )
            firestore.collection("Valutazioni")
                .add(valutazioneEsercizio)
                .addOnSuccessListener {
                    Toast.makeText(parent.context, "Grazie della valutazione", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Log.d("DBVALUTAZIONI", "Eccezione $it occurred")
                }
        }
        return holder
    }

    override fun onBindViewHolder(holder: ListaEserciziViewHolder, position: Int) {
        holder.itemTextNomeEsercizio.text = data.get(position).esercizio.name
        holder.itemTextBodyPart.text = data.get(position).esercizio.bodyPart
        holder.itemTextTarget.text = data.get(position).esercizio.target
        holder.webView.loadUrl(data.get(position).esercizio.gifUrl)
        holder.itemTextInstructions.text = data.get(position).esercizio.instructions
        holder.itemTextEquipment.text = data.get(position).esercizio.equipment
        holder.itemTextSecondaryMuscles.text = data.get(position).esercizio.secondaryMuscles
        holder.itemEditTextSerieNumber.text = data.get(position).serie
        holder.itemEditTextRepNumber.text = data.get(position).rep
        holder.itemEditTextKgNumber.text = data.get(position).peso
    }



    override fun getItemCount(): Int {
        return data.size
    }

}