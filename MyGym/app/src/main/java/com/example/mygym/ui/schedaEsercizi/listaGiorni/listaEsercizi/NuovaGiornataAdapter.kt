package com.example.mygym.ui.schedaEsercizi.listaGiorni.listaEsercizi

import android.app.AlertDialog
import android.content.ContentResolver
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.mygym.Esercizio
import com.example.mygym.EsercizioPerUtente
import com.example.mygym.R
import com.example.mygym.databinding.ItemViewEsercizioBinding
import com.example.mygym.ui.schedaEsercizi.listaGiorni.ListaGiorniFragmentArgs
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.lang.Character.UnicodeBlock
import java.lang.Character.UnicodeScript

class NuovaGiornataAdapter(val data: List<Esercizio>): RecyclerView.Adapter<NuovaGiornataAdapter.NuovaGiornataViewHolder>() {

    private val textAggiunto = "Aggiunto ✔"
    private val textDaAggiungere = "Aggiungere?"
    val listaEserciziPerUtente = mutableListOf<EsercizioPerUtente>()
    var posizione = 0

    class NuovaGiornataViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
        val itemTextNomeEsercizio :TextView = row.findViewById(R.id.itemTextNomeEsercizio)
        val itemTextBodyPart : TextView = row.findViewById(R.id.itemTextBodyPart)
        val itemTextTarget : TextView = row.findViewById(R.id.itemTextTarget)
        val webView : WebView = row.findViewById(R.id.webView)
        val itemDefinitiveTextInstructions : TextView = row.findViewById(R.id.itemDefinitiveTextInstructions)
        val itemTextInstructions : TextView = row.findViewById(R.id.itemTextInstructions)
        val itemDefinitiveTextEquipment : TextView = row.findViewById(R.id.itemDefinitiveTextEquipment)
        val itemTextEquipment : TextView = row.findViewById(R.id.itemTextEquipment)
        val itemEditTextSerieNumber : TextView = row.findViewById(R.id.itemEditTextSerieNumber)
        val itemEditTextRepNumber : TextView = row.findViewById(R.id.itemEditTextRepNumber)
        val itemEditTextKgNumber : TextView = row.findViewById(R.id.itemEditTextKgNumber)
        val itemButtonAggiungiEsercizio : Button = row.findViewById(R.id.itemButtonAggiungiEsercizio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NuovaGiornataViewHolder {
        val layout = LayoutInflater.from(parent.context).
            inflate(R.layout.item_view_esercizio, parent, false)
        val holder = NuovaGiornataViewHolder(layout)
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
                click = false
            }else {
                holder.itemDefinitiveTextInstructions.visibility = View.VISIBLE
                holder.itemTextInstructions.visibility = View.VISIBLE
                holder.itemDefinitiveTextEquipment.visibility = View.VISIBLE
                holder.itemTextEquipment.visibility = View.VISIBLE
                click = true

            }
            true
        }
        holder.itemButtonAggiungiEsercizio.setOnClickListener{
            val esercizioPerUtente = EsercizioPerUtente(
                data.get(posizione),
                holder.itemEditTextSerieNumber.text.toString(),
                holder.itemEditTextRepNumber.text.toString(),
                holder.itemEditTextKgNumber.text.toString()
            )
            if (holder.itemButtonAggiungiEsercizio.text != textAggiunto) {
                if (holder.itemEditTextRepNumber.text.isNotEmpty() && holder.itemEditTextSerieNumber.text.isNotEmpty() && holder.itemEditTextKgNumber.text.isNotEmpty()) {
                    listaEserciziPerUtente.add(esercizioPerUtente)
                    holder.itemButtonAggiungiEsercizio.text = textAggiunto
                    /*
                    dbRef.document(giorno)
                        .set(esercizio).addOnCompleteListener {
                            if (it.isSuccessful) {

                            } else {
                                Toast.makeText(
                                    parent.context,
                                    "An Error Occurred",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                     */
                } else {
                    Toast.makeText(
                        parent.context,
                        "Inserire valori per le ripetizioni, serie e Kg",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }else{
                listaEserciziPerUtente.remove(esercizioPerUtente)
                holder.itemButtonAggiungiEsercizio.text = textDaAggiungere
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: NuovaGiornataViewHolder, position: Int) {
        holder.itemTextNomeEsercizio.text = data.get(position).name
        holder.itemTextBodyPart.text = data.get(position).bodyPart
        holder.itemTextTarget.text = data.get(position).target
        holder.webView.loadUrl(data.get(position).gifUrl)
        holder.itemTextInstructions.text = data.get(position).instructions
        holder.itemTextEquipment.text = data.get(position).equipment
        posizione = position
    }



    override fun getItemCount(): Int {
        return data.size
    }

}