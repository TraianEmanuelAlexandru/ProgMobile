package com.example.mygym

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EsercizioDao {
    @Query("SELECT * FROM esercizi")
    fun getListaEsercizi(): List<Esercizio>
    @Query("SELECT * FROM eserciziperutente")
    fun getListaEserciziPerUtente(): List<EsercizioPerUtente>

    /*
    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Esercizio>

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): Esercizio
    */
    @Insert
    fun insertInListaEsercizi(vararg esercizi: Esercizio)

    @Insert
    fun insertInListaEserciziPerUtente(vararg esercizioPerUtente: EsercizioPerUtente)

    @Delete
    fun deleteEsercizio(user: Esercizio)

    @Delete
    fun deleteEsercizioPerUtente(user: EsercizioPerUtente)
}