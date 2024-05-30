package com.example.mygym.ui.schedaEsercizi

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mygym.DatabaseService
import com.example.mygym.Giorno
import com.example.mygym.databinding.FragmentSchedaEserciziBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.Retrofit

class SchedaEserciziFragment : Fragment() {

    private var _binding: FragmentSchedaEserciziBinding? = null
    lateinit var dbRef :DatabaseReference
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val schedaEserciziViewModel : SchedaEserciziViewModel by viewModels()
        _binding = FragmentSchedaEserciziBinding.inflate(inflater, container, false)
        val root: View = binding.root
        dbRef = FirebaseDatabase.getInstance().getReference("Esercizio")
        val db = FirebaseFirestore.getInstance()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://mygym-c1d3c-default-rtdb.europe-west1.firebasedatabase.app/")
            .build()

        val databaseService =retrofit.create(DatabaseService::class.java)

        binding.editTextInserireEmail.setOnClickListener{
            val email = binding.editTextInserireEmail.text.toString()
            if (email != null) {


                /*val idEsercizio = dbRef.push().key!!
                val esercizio = Esercizio(idEsercizio,"leg_press", 3, 12, 30, email)
                dbRef.child(idEsercizio).setValue(esercizio)
                    .addOnCompleteListener{
                        Toast.makeText(activity, "Esercizio Inserito con successo", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener{err->
                        Toast.makeText(activity, "Error ${err.message}", Toast.LENGTH_SHORT).show()

                    }*/
            }
        }



        binding.buttonAggiungiGiornata.setOnClickListener{
            /*db.collection("Esercizio")
                .add(esercizio)
                .addOnCompleteListener {
                    if (it.isSuccessful)
                        Toast.makeText(activity, "Esercizio Inserito con successo", Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(activity, "Esercizio Non Inserito", Toast.LENGTH_SHORT).show()
                }*/
        }

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}