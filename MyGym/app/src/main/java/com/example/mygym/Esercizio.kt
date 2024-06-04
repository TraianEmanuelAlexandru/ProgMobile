package com.example.mygym

import org.json.JSONArray

data class Esercizio(
    var id: String,
    var name: String,
    var bodyPart: String,
    var equipment: String,
    var gifUrl: String,
    var target: String,
    var secondaryMuscles: JSONArray,
    var instructions: JSONArray
)

