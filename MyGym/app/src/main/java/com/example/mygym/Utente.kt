package com.example.mygym

import com.google.firebase.Timestamp
import java.time.LocalDate

data class Utente(
    var dataIscrizione: LocalDate,
    var dataScadenza: LocalDate,
    var presente: Boolean
)

