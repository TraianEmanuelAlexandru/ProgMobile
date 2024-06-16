package com.example.mygym

import org.json.JSONArray

data class Esercizio(
    var name: String = "",
    var bodyPart: String = "",
    var equipment: String = "",
    var gifUrl: String = "",
    var target: String = "",
    var secondaryMuscles : String = "",
    var instructions: String = ""
)
data class EsercizioPerUtente(
    var esercizio: Esercizio = Esercizio(),
    var serie: String = "",
    var rep: String = "",
    var peso: String = ""
)
