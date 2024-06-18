package com.example.mygym.ui.home

import android.graphics.Bitmap
import androidx.core.content.ContextCompat.createAttributionContext
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class HomeViewModel : ViewModel() {
    var numeroUtentiPresenti: Int = 0

    private val _progressBar = MutableLiveData<Int>()
    val progBar: LiveData<Int> = _progressBar
    val firestore = FirebaseFirestore.getInstance()
    fun verificaNumPersone(){
        firestore.collection("Utenti")
            .whereEqualTo("presente", true).get()
            .addOnSuccessListener {
                documents->
                var numero = 0
                for (doc in documents){
                    numero += 1
                }
                numeroUtentiPresenti= numero
            }
        _progressBar.value = numeroUtentiPresenti
    }
}