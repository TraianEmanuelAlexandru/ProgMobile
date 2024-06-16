package com.example.mygym

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "Esercizi")
data class Esercizio(
    @PrimaryKey var name: String = "",
    var bodyPart: String = "",
    var equipment: String = "",
    var gifUrl: String = "",
    var target: String = "",
    var secondaryMuscles : String = "",
    var instructions: String = ""
)
@Entity(tableName = "EserciziPerUtente")
data class EsercizioPerUtente(
    @Embedded @PrimaryKey var esercizio: Esercizio = Esercizio(),
    var serie: String = "",
    var rep: String = "",
    var peso: String = ""
)
