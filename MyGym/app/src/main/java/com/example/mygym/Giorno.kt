package com.example.mygym

import android.media.Image

data class Giorno(val esercizi: ArrayList<Esercizio>?) {

    data class Esercizio(var idEsercizio: String, var nome: String, var serie: Int, var ripetizioni: Int, var peso: Int, var rif_utente: String)

}