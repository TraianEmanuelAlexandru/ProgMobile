package com.example.mygym.ui.schedaEsercizi.listaGiorni.listaEsercizi

import android.app.AlertDialog
import android.content.ContentResolver
import android.provider.Settings.Global.getString
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceResponse
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
import com.google.android.material.button.MaterialButton
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Character.UnicodeBlock
import java.lang.Character.UnicodeScript
import kotlin.concurrent.thread

class NuovaGiornataAdapter(val data: List<Esercizio>, val listaEserciziPerUtente: MutableList<EsercizioPerUtente>): RecyclerView.Adapter<NuovaGiornataAdapter.NuovaGiornataViewHolder>() {

    private val textAggiunto = "Aggiunto ✔"
    private val textDaAggiungere = "Aggiungere?"


    class NuovaGiornataViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
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
        val itemButtonAggiungiEsercizio : MaterialButton = row.findViewById(R.id.itemButtonAggiungiEsercizio)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NuovaGiornataViewHolder {
        val layout = LayoutInflater.from(parent.context).
            inflate(R.layout.item_view_esercizio, parent, false)
        val holder = NuovaGiornataViewHolder(layout)
        val bottoneCliccato = holder.itemButtonAggiungiEsercizio.text.toString()
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
        holder.itemButtonAggiungiEsercizio.setOnClickListener{
            if (holder.itemEditTextRepNumber.text.isNotEmpty() && holder.itemEditTextSerieNumber.text.isNotEmpty()) {
                val esercizioPerUtente = EsercizioPerUtente(
                    data.get(holder.adapterPosition),
                    holder.itemEditTextSerieNumber.text.toString(),
                    holder.itemEditTextRepNumber.text.toString(),
                    holder.itemEditTextKgNumber.text.toString()
                )

                if (holder.itemButtonAggiungiEsercizio.text == textDaAggiungere) {
                    listaEserciziPerUtente.add(esercizioPerUtente)
                    holder.itemButtonAggiungiEsercizio.text = textAggiunto

                } else {
                    if(listaEserciziPerUtente.remove(esercizioPerUtente)){
                        Toast.makeText(parent.context, "Conferma per salvare le modifiche", Toast.LENGTH_SHORT).show()
                        holder.itemButtonAggiungiEsercizio.text = textDaAggiungere
                        holder.itemEditTextSerieNumber.text = ""
                        holder.itemEditTextRepNumber.text = ""
                        holder.itemEditTextKgNumber.text = ""
                    }
                }
            }else {
                Toast.makeText(
                    parent.context,
                    "Inserire valori per le ripetizioni, serie e Kg",
                    Toast.LENGTH_SHORT
                ).show()
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
        holder.itemTextSecondaryMuscles.text = data.get(position).secondaryMuscles


        for (esercizioPerUtente in listaEserciziPerUtente) {
            if (esercizioPerUtente.esercizio.name.equals(data.get(position).name)) {
                holder.itemButtonAggiungiEsercizio.text = textAggiunto
                holder.itemEditTextSerieNumber.text = esercizioPerUtente.serie
                holder.itemEditTextRepNumber.text = esercizioPerUtente.rep
                holder.itemEditTextKgNumber.text = esercizioPerUtente.peso
                break
            } else {
                holder.itemButtonAggiungiEsercizio.text = textDaAggiungere
                holder.itemEditTextSerieNumber.text = ""
                holder.itemEditTextRepNumber.text = ""
                holder.itemEditTextKgNumber.text = ""
            }
        }
    }



    override fun getItemCount(): Int {
        return data.size
    }

}
