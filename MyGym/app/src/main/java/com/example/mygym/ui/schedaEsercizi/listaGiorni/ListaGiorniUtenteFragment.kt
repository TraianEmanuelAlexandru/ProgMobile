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
import com.example.mygym.EsercizioRoomDatabase
import com.example.mygym.Giorno
import com.example.mygym.R
import com.example.mygym.databinding.FragmentListaGiorniUtenteBinding
import com.example.mygym.ui.schedaEsercizi.SchedeUtenteFragmentDirections
import com.google.firebase.firestore.FirebaseFirestore

class ListaGiorniUtenteFragment : Fragment() {
    private var _binding: FragmentListaGiorniUtenteBinding? = null
    private val binding get() = _binding!!
    private lateinit var firestore: FirebaseFirestore
    private lateinit var listaGiornate: List<Giorno>

    val argomentoFromOnline : ListaGiorniUtenteFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaGiorniUtenteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val sharedPref = this.activity?.getSharedPreferences(getString(R.string.profile_key), Context.MODE_PRIVATE)
        val myEmail = sharedPref!!.getString("email", "")
        val fromOnlineState = argomentoFromOnline.argomentoDaSchedeUtenteToListaGiorniUtente

        firestore = FirebaseFirestore.getInstance()
        val recyclerView = binding.recyclerViewListaGiorniUtente
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        var listaGiorni = mutableListOf<String>()
        val giornoDao = EsercizioRoomDatabase.getInstance(requireContext()).giornoDao()


        if (fromOnlineState) {
            binding.buttonAggiungiGiornataPersonaleUtente.visibility = View.GONE
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
                        recyclerView.adapter =
                            ListaGiorniAdapter(listaGiorni, myEmail, fromUser = true)
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Exception $it occurred", Toast.LENGTH_SHORT)
                        .show()
                }
        }else{
            binding.buttonAggiungiGiornataPersonaleUtente.visibility = View.VISIBLE

            listaGiornate = giornoDao.getListaGiorni()

            recyclerView.adapter = ListaGiorniUtenteAdapter(listaGiornate)
        }

        binding.buttonAggiungiGiornataPersonaleUtente.setOnClickListener {
            it.findNavController().navigate(R.id.action_fragment_lista_giorni_utente_to_fragment_nuova_giornata_utente)
        }
        return root
    }
}