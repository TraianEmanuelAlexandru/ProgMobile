package com.example.mygym.ui.schedaEsercizi.listaGiorni

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mygym.R
import com.example.mygym.databinding.FragmentListaGiorniBinding
import com.google.firebase.firestore.FirebaseFirestore

class ListaGiorniFragment : Fragment() {
    private var _binding: FragmentListaGiorniBinding? = null
    private val binding get() = _binding!!
    val argomentoListaToGiorni: ListaGiorniFragmentArgs by navArgs()
    private lateinit var firestore: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListaGiorniBinding.inflate(inflater, container, false)
        val root: View = binding.root

        firestore = FirebaseFirestore.getInstance()
        val emailUtente = argomentoListaToGiorni.argomentoDaListaUtentiToListaGiorni
        val recyclerView = binding.recyclerViewListaGiorni
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val listaGiorni = mutableListOf<String>()

        firestore.collection(getString(R.string.collectionUtenti))
            .document(emailUtente)
            .collection(getString(R.string.collectionEserciziPerGiorno))
            .get()
            .addOnSuccessListener {
                documents->
                if (documents.isEmpty){
                    Toast.makeText(requireContext(), "Nessun Giorno trovato per questo Utente", Toast.LENGTH_SHORT).show()
                }else {
                    for (document in documents) {
                        listaGiorni.add(document.id)
                    }
                    recyclerView.adapter = ListaGiorniAdapter(listaGiorni)
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Exception $it occurred", Toast.LENGTH_SHORT).show()
            }
        binding.buttonAggiungiGiornata.setOnClickListener{
            it.findNavController().navigate(R.id.fragment_nuova_giornata)
            /*
            val numeroGiornate = recyclerView.adapter?.itemCount
            if (numeroGiornate != null) {
                firestore.collection(getString(R.string.collectionUtenti))
                    .document(emailUtente)
                    .collection(getString(R.string.collectionEserciziPerGiorno))
                    .add("Giornata $numeroGiornate")
            }else{
                firestore.collection(getString(R.string.collectionUtenti))
                    .document(emailUtente)
                    .collection(getString(R.string.collectionEserciziPerGiorno))
                    .document("Giornata" + 1.toString())
            }

             */
        }

        return root
    }

}