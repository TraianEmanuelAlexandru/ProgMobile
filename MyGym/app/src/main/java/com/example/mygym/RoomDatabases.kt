package com.example.mygym

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope

@Database(entities = [EsercizioPerUtente::class, Giorno::class], version = 1)
abstract class EsercizioRoomDatabase : RoomDatabase() {
    //abstract fun esercizioPerUtenteDao(): EsercizioPerUtenteDao
    abstract fun giornoDao(): GiornoDao

    private class EsercizioDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

        }
    }

    companion object {
        @Volatile
        private var INSTANCE: EsercizioRoomDatabase? = null

        fun getInstance(
            context: Context
        ): EsercizioRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    EsercizioRoomDatabase::class.java,
                    "esercizio_database"
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}

/*
@Database(entities = [Giorno::class], version = 1)
abstract class GiorniRoomDatabase : RoomDatabase() {
    abstract fun giorniDao(): GiorniDao

    companion object {
        @Volatile
        private var INSTANCE: GiorniRoomDatabase? = null

        fun getInstance(
            context: Context
        ): GiorniRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    GiorniRoomDatabase::class.java,
                    "giorni_database"
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}

 */