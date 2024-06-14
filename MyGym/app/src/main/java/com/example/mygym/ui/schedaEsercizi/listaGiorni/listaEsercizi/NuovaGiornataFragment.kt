package com.example.mygym.ui.schedaEsercizi.listaGiorni.listaEsercizi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygym.Esercizio
import com.example.mygym.R
import com.example.mygym.databinding.FragmentNuovaGiornataBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class NuovaGiornataFragment : Fragment() {
    private var _binding: FragmentNuovaGiornataBinding? = null
    private val binding get() = _binding!!
    private lateinit var firestore: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNuovaGiornataBinding.inflate(inflater, container, false)
        val root: View = binding.root

        firestore = FirebaseFirestore.getInstance()
        val db = firestore.collection(getString(R.string.collectionEsercizi))

        val listaEsercizi = mutableListOf<Esercizio>()
        val recyclerView = binding.recyclerViewListaEsercizi
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.editTextRicercaEsercizio.setOnClickListener{
            val ricercaEsercizio = binding.editTextRicercaEsercizio.text.toString()
            if (ricercaEsercizio== ""){
                db.get().addOnSuccessListener {
                    documents->
                    for (document in documents){
                        val id = document.data
                        val esercizio = Esercizio(
                            id.get("name").toString(),
                            id.get("bodyPart").toString(),
                            id.get("equipment").toString(),
                            id.get("gifUrl").toString(),
                            id.get("target").toString(),
                            id.get("secondaryMuscles").toString(),
                            id.get("instructions").toString()
                        )
                        listaEsercizi.add(esercizio)
                    }
                    recyclerView.adapter = NuovaGiornataAdapter(listaEsercizi)
                }.addOnFailureListener {
                    Toast.makeText(requireContext(), "Exception $it occurred", Toast.LENGTH_SHORT).show()
                }
            }else{
                db.whereEqualTo("name", ricercaEsercizio)
                    .get().addOnSuccessListener{
                        documents->
                        for (document in documents){
                            listaEsercizi.add(document.toObject<Esercizio>())
                        }
                        recyclerView.adapter = NuovaGiornataAdapter(listaEsercizi)
                    }.addOnFailureListener{
                        Toast.makeText(requireContext(), "Exception $it occurred", Toast.LENGTH_SHORT).show()
                    }
            }
        }


        return root
    }
}