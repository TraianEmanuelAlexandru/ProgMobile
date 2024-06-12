package com.example.mygym.ui.schedaEsercizi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListaUtentiViewModel : ViewModel() {

    private val _numeroGiornate = MutableLiveData<Int>(0)
    val numeroGiornate: LiveData<Int>  =  _numeroGiornate

    fun incrementNumber(){
        _numeroGiornate.value = _numeroGiornate.value!! + 1
    }
}