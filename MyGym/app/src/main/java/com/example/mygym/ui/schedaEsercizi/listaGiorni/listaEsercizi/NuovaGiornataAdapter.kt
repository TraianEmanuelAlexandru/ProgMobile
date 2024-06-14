package com.example.mygym.ui.schedaEsercizi.listaGiorni.listaEsercizi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mygym.Esercizio
import com.example.mygym.R

class NuovaGiornataAdapter(val data: List<Esercizio>): RecyclerView.Adapter<NuovaGiornataAdapter.NuovaGiornataViewHolder>() {


    class NuovaGiornataViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
        val itemTextNomeEsercizio :TextView = row.findViewById(R.id.itemTextNomeEsercizio)
        val itemTextBodyPart : TextView = row.findViewById(R.id.itemTextBodyPart)
        val itemTextTarget : TextView = row.findViewById(R.id.itemTextTarget)
        val webView : WebView = row.findViewById(R.id.webView)
        val itemEditTextSerieNumber : TextView = row.findViewById(R.id.itemEditTextSerieNumber)
        val itemEditTextRepNumber : TextView = row.findViewById(R.id.itemEditTextRepNumber)
        val itemEditTextKgNumber : TextView = row.findViewById(R.id.itemEditTextKgNumber)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NuovaGiornataViewHolder {
        val layout = LayoutInflater.from(parent.context).
            inflate(R.layout.item_view_esercizio, parent, false)
        val holder = NuovaGiornataViewHolder(layout)
        holder.webView.webViewClient


        holder.webView.settings.useWideViewPort = true
        holder.webView.settings.loadWithOverviewMode = true

        holder.row.setOnClickListener{
            /*
            val argomento = holder.itemTextNumeroGiorno.text.toString()
            val action = ListaGiorniFragmentDirections.actionFragmentListaGiorniToFragmentListaEsercizi(argomento)
            it.findNavController().navigate(action)
            Toast.makeText(parent.context, "Hai cliccato $argomento ", Toast.LENGTH_SHORT).show()

             */
        }
        return holder
    }

    override fun onBindViewHolder(holder: NuovaGiornataViewHolder, position: Int) {
        holder.itemTextNomeEsercizio.text = data.get(position).name
        holder.itemTextBodyPart.text = data.get(position).bodyPart
        holder.itemTextTarget.text = data.get(position).target
        holder.webView.loadUrl(data.get(position).gifUrl)
    }



    override fun getItemCount(): Int {
        return data.size
    }

}