package com.example.mygym.ui.schedaEsercizi.listaGiorni.listaEsercizi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygym.R
import com.example.mygym.databinding.FragmentListaEserciziBinding
import com.example.mygym.databinding.FragmentListaGiorniBinding
import com.google.firebase.firestore.FirebaseFirestore

class ListaEserciziFragment : Fragment() {
    private var _binding : FragmentListaEserciziBinding? = null
    private val binding get() = _binding!!
    private lateinit var firestore: FirebaseFirestore
    val argomentoListaGiorniToListaEsercizi: ListaEserciziFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaEserciziBinding.inflate(inflater, container, false)
        val root: View = binding.root

        firestore = FirebaseFirestore.getInstance()
        val emailUtente = argomentoListaGiorniToListaEsercizi.argomentoEmailDaListaGiorniToListaEsercizi
        val giorno = argomentoListaGiorniToListaEsercizi.argomentoGiornoDaListaGiorniToListaEsercizi
        val recyclerView = binding.recyclerViewListaEsercizi
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val dbRefEserciziPerGiorno = firestore.collection(getString(R.string.collectionUtenti))
            .document(emailUtente)
            .collection(getString(R.string.collectionEserciziPerGiorno))
            .document(giorno)
        dbRefEserciziPerGiorno.get().addOnSuccessListener {
            document->
            val listaEsercizi = document.data!!.get("listaEsercizi")
            recyclerView.adapter =

        }

        return root
    }

}