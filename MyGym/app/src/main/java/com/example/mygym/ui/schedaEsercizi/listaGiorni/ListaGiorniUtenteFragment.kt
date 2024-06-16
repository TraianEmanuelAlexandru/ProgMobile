package com.example.mygym.ui.schedaEsercizi.listaGiorni

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygym.R
import com.example.mygym.databinding.FragmentListaGiorniUtenteBinding
import com.example.mygym.ui.schedaEsercizi.SchedeUtenteFragmentDirections
import com.google.firebase.firestore.FirebaseFirestore

class ListaGiorniUtenteFragment : Fragment() {
    private var _binding: FragmentListaGiorniUtenteBinding? = null
    private val binding get() = _binding!!
    private lateinit var firestore: FirebaseFirestore
    val fromOnline : ListaGiorniUtenteFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaGiorniUtenteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val sharedPref = this.activity?.getSharedPreferences(getString(R.string.profile_key), Context.MODE_PRIVATE)
        val myEmail = sharedPref!!.getString("email", "")
        val fromOnlineState = fromOnline.argomentoDaSchedeUtenteToListaGiorniUtente

        firestore = FirebaseFirestore.getInstance()
        val recyclerView = binding.recyclerViewListaGiorniUtente
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val listaGiorni = mutableListOf<String>()

        firestore.collection(getString(R.string.collectionUtenti))
            .document(myEmail!!)
            .collection(getString(R.string.collectionEserciziPerGiorno))
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    Toast.makeText(
                        requireContext(),
                        "Nessun Giorno trovato per questo Utente",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    for (document in documents) {
                        listaGiorni.add(document.id)
                    }
                    recyclerView.adapter = ListaGiorniAdapter(listaGiorni, myEmail, fromUser = true)
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Exception $it occurred", Toast.LENGTH_SHORT)
                    .show()
            }
        return root
    }
}