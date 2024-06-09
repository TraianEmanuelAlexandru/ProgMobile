package com.example.mygym

import com.google.firebase.Timestamp
import java.time.LocalDate

data class Utente(
    var dataIscrizione: Timestamp,
    var dataScadenza: Timestamp,
    var presente: Boolean
)

