package com.example.mygym

import androidx.navigation.findNavController
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    var nomeEsercizio = "a"
    var bodyEsercizio = "null"
    var targetEsercizio = "back"

    @Test
    fun setFilter(): Unit {
        if (nomeEsercizio.isEmpty()) {
            if (bodyEsercizio.isEmpty()) {
                if (targetEsercizio.isEmpty()) {
                    Filter.and(
                        Filter.greaterThanOrEqualTo("name", "a"),
                        Filter.lessThan("name", "z")
                    )
                } else {
                    Filter.and(
                        Filter.greaterThanOrEqualTo("target", targetEsercizio),
                        Filter.lessThan("target", targetEsercizio + "z")
                    )
                }
            } else {
                if (targetEsercizio.isEmpty()) {
                    Filter.and(
                        Filter.greaterThanOrEqualTo("bodyPart", bodyEsercizio),
                        Filter.lessThan("bodyPart", bodyEsercizio + "z")
                    )
                } else {
                    Filter.and(
                        Filter.greaterThanOrEqualTo("target", targetEsercizio),
                        Filter.lessThan("target", targetEsercizio + "z"),
                        Filter.greaterThanOrEqualTo("bodyPart", bodyEsercizio),
                        Filter.lessThan("bodyPart", bodyEsercizio + "z")
                    )
                }
            }
        } else {
            if (bodyEsercizio.isEmpty()) {
                if (targetEsercizio.isEmpty()) {
                    Filter.and(
                        Filter.greaterThanOrEqualTo("name", nomeEsercizio),
                        Filter.lessThan("name", nomeEsercizio + "z")
                    )
                } else {
                    Filter.and(
                        Filter.greaterThanOrEqualTo("name", nomeEsercizio),
                        Filter.lessThan("name", nomeEsercizio + "z"),
                        Filter.greaterThanOrEqualTo("target", targetEsercizio),
                        Filter.lessThan("target", targetEsercizio + "z")
                    )
                }
            } else {
                if (targetEsercizio.isEmpty()) {
                    Filter.and(
                        Filter.greaterThanOrEqualTo("name", nomeEsercizio),
                        Filter.lessThan("name", nomeEsercizio + "z"),
                        Filter.greaterThanOrEqualTo("bodyPart", bodyEsercizio),
                        Filter.lessThan("bodyPart", bodyEsercizio + "z")
                    )
                } else {
                    Filter.and(
                        Filter.greaterThanOrEqualTo("name", nomeEsercizio),
                        Filter.lessThan("name", nomeEsercizio + "z"),
                        Filter.greaterThanOrEqualTo("target", targetEsercizio),
                        Filter.lessThan("target", targetEsercizio + "z"),
                        Filter.greaterThanOrEqualTo("bodyPart", bodyEsercizio),
                        Filter.lessThan("bodyPart", bodyEsercizio + "z")
                    )
                }
            }
        }
    }

}