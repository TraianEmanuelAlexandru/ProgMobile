package com.example.mygym.ui.schedaEsercizi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mygym.R

class SchedaEserciziAdapter(val data: List<String>): RecyclerView.Adapter<SchedaEserciziAdapter.SchedaEserciziViewHolder>() {


    class SchedaEserciziViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
        val numeroGiornate :TextView = row.findViewById<TextView>(R.id.numeroGiornata)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchedaEserciziViewHolder {
        val layout = LayoutInflater.from(parent.context).
            inflate(R.layout.item_view_scheda, parent, false)
        val holder = SchedaEserciziViewHolder(layout)

        holder.row.setOnClickListener{
            Toast.makeText(parent.context, "Hai cliccato ", Toast.LENGTH_SHORT).show()
        }
        return holder
    }

    override fun onBindViewHolder(holder: SchedaEserciziViewHolder, position: Int) {
        holder.numeroGiornate.text = data.get(position).toString()
    }



    override fun getItemCount(): Int {
        return data.size
    }

}