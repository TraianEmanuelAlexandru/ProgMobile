package com.example.mygym

import com.google.firebase.Timestamp
import java.time.LocalDate

data class Utente(
    var emailUtente: String,
    var dataIscrizione: Timestamp,
    var dataScadenza: Timestamp,
    var presente: Boolean
)

data class Valutazione(
    var email: String,
    var giornata: Int,
    var recensione: String,
    var valutazione: Float
)

