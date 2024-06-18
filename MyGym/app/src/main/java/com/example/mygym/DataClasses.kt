package com.example.mygym

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Esercizio(
    @PrimaryKey var name: String = "",
    var bodyPart: String = "",
    var equipment: String = "",
    var gifUrl: String = "",
    var target: String = "",
    var secondaryMuscles : String = "",
    var instructions: String = ""
)

@Entity(tableName = "Esercizi",primaryKeys = arrayOf("name", "serie", "rep"))
data class EsercizioPerUtente(

    @Embedded var esercizio: Esercizio = Esercizio(),
    var serie: String = "",
    var rep: String = "",
    var peso: String = ""
)
/*
@Entity(tableName = "EserciziPerUtente")
data class EsercizioPerUtente(
    @PrimaryKey (autoGenerate = true) val idEsercizioPerUtente : Int ,
    @Embedded var esercizio: Esercizio = Esercizio(),
    var serie: String = "",
    var rep: String = "",
    var peso: String = ""
)
*/
@Entity(tableName = "Giorni")
data class Giorno(
    @ColumnInfo("numero_giorno") var numeroGiorno : Int,
    @Embedded var esercizioPerUtente: EsercizioPerUtente
){
    @PrimaryKey (autoGenerate = true) var idGiorno: Int? = null
}
