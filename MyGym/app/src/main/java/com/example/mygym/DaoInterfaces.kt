package com.example.mygym

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface EsercizioPerUtenteDao {
    @Query("SELECT * FROM esercizi")
    fun getListaEsercizi(): List<EsercizioPerUtente>
    /*
    @Query("SELECT * FROM eserciziperutente")
    fun getListaEserciziPerUtente(): List<EsercizioPerUtente>


    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Esercizio>

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): Esercizio

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertInListaEserciziPerUtente(vararg esercizioPerUtente: EsercizioPerUtente)


    @Delete
    fun deleteEsercizioPerUtente(user: EsercizioPerUtente)
    */
    @Insert
    fun insertInListaEsercizi(vararg esercizi: EsercizioPerUtente)



    @Delete
    fun deleteEsercizio(esercizio: EsercizioPerUtente)

}


@Dao
interface GiornoDao {
    @Query("SELECT * FROM giorni")
    fun getListaGiorni(): List<Giorno>
    @Update
    fun updateGiorno(vararg giorno: Giorno )
    @Insert
    fun insertGiorno(vararg giorno: Giorno)

    @Delete
    fun deleteEsercizio(giorno: Giorno)

}
