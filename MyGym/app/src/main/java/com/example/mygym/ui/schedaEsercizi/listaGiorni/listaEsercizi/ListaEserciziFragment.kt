package com.example.mygym.ui.schedaEsercizi.listaGiorni.listaEsercizi

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygym.Esercizio
import com.example.mygym.EsercizioPerUtente
import com.example.mygym.R
import com.example.mygym.databinding.FragmentListaEserciziBinding
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class ListaEserciziFragment : Fragment() {
    private var _binding: FragmentListaEserciziBinding? = null
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
        val emailUtente =
            argomentoListaGiorniToListaEsercizi.argomentoEmailDaListaGiorniUtenteToListaEsercizi
        val giorno =
            argomentoListaGiorniToListaEsercizi.argomentoGiornoDaListaGiorniUtenteToListaEsercizi

        val recyclerView = binding.recyclerViewListaEsercizi
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val dbRefEserciziPerGiorno = firestore.collection(getString(R.string.collectionUtenti))
            .document(emailUtente)
            .collection(getString(R.string.collectionEserciziPerGiorno))
            .document(giorno)

        dbRefEserciziPerGiorno.get()
            .addOnSuccessListener { document ->
                val listaEsercizi = setListaEsercizi(document)

                recyclerView.adapter = ListaEserciziAdapter(listaEsercizi, true)
            }.addOnFailureListener {
                Log.d(
                    "ERRORE",
                    "Exception $it occurred---------------------------------------------------------------------------------------"
                )
            }

        return root
    }

    fun setListaEsercizi(document: DocumentSnapshot): MutableList<EsercizioPerUtente> {
        val chiavi = document.data!!.keys
        val listaEserciziPerUtente = mutableListOf<EsercizioPerUtente>()

        for (i in chiavi) {
            val documento = document.data!!.getValue(i) as HashMap<String, Any>
            val esercizioFisso = documento.get("esercizio") as HashMap<String, String>
            val esercizio = Esercizio(
                esercizioFisso.get("name").toString(),
                esercizioFisso.get("bodyPart").toString(),
                esercizioFisso.get("equipment").toString(),
                esercizioFisso.get("gifUrl").toString(),
                esercizioFisso.get("target").toString(),
                esercizioFisso.get("secondaryMuscles").toString(),
                esercizioFisso.get("instructions").toString()
            )

            val esercizioPerUtente = EsercizioPerUtente(
                esercizio,
                documento.get("serie").toString(),
                documento.get("rep").toString(),
                documento.get("peso").toString()
            )
            listaEserciziPerUtente.add(esercizioPerUtente)
        }
        return listaEserciziPerUtente
    }

}