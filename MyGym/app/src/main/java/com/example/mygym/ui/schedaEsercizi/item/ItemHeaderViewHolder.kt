package com.example.mygym.ui.schedaEsercizi.item

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mygym.R
import java.lang.ref.WeakReference

class ItemHeaderViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {

    private val view = WeakReference(itemView)

    private var textView : TextView? = null

    var content: String = ""

            init{
                view.get()?.let {
                    textView = it.findViewById(R.id.numeroGiornata)
                }
            }
    fun updateView(){
        textView?.text = content
    }
}