package com.example.mygym.ui.schedaEsercizi.listaGiorni.listaEsercizi

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygym.Esercizio
import com.example.mygym.EsercizioPerUtente
import com.example.mygym.EsercizioRoomDatabase
import com.example.mygym.Giorno
import com.example.mygym.R
import com.example.mygym.databinding.FragmentNuovaGiornataBinding
import com.example.mygym.databinding.FragmentNuovaGiornataUtenteBinding
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.HashMap

class NuovaGiornataUtenteFragment : Fragment() {
    private var _binding: FragmentNuovaGiornataUtenteBinding? = null
    private val binding get() = _binding!!
    private lateinit var firestore: FirebaseFirestore
    private lateinit var filter: Filter
    private lateinit var listaEsercizi: MutableList<Esercizio>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNuovaGiornataUtenteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        firestore = FirebaseFirestore.getInstance()
        val dbRefEsercizi = firestore.collection(getString(R.string.collectionEsercizi))

        val recyclerView = binding.recyclerViewListaEsercizi
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        listaEsercizi = mutableListOf()
        var listaEserciziPerUtente  = mutableListOf<EsercizioPerUtente>()
        //val numeroGiorni = argomentoNumeroGiorni.argomentoNumeroGiorniDaListaGiorniUtenteToNuovaGiornataUtente

        binding.buttonAvviaFiltroUtente.setOnClickListener{
            val nomeEsercizio = binding.editTextFiltroNomeEsercizio.text.toString()
            val bodyEsercizio = binding.editTextFiltroCorpoEsercizio.text.toString()
            val targetEsercizio = binding.editTextFiltroTargetEsercizio.text.toString()
            filter = NuovaGiornataFragment().setFilter(nomeEsercizio,bodyEsercizio, targetEsercizio)
            Toast.makeText(requireContext(),"Caricamento dati in corso...\nAttendere prego", Toast.LENGTH_SHORT).show()
            dbRefEsercizi.where(filter).get().addOnSuccessListener {
                    documents->
                listaEsercizi = NuovaGiornataFragment().setListaEsercizi(documents)
                recyclerView.adapter = NuovaGiornataAdapter(listaEsercizi, listaEserciziPerUtente)
            }.addOnFailureListener {
                Toast.makeText(requireContext(), "Exception $it occurred", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonConfermaListaEserciziUtente.setOnClickListener{
            if (listaEserciziPerUtente.isNotEmpty()){
                val giornoDao = EsercizioRoomDatabase.getInstance(requireContext()).giornoDao()
                val numGiorno = (0..100).random()
                val giornoDiVerifica = giornoDao.getGiorno(numGiorno)
                if (giornoDiVerifica == null) {
                    for (esercizio in listaEserciziPerUtente) {
                        //esercizioPerUtenteDao.insertInListaEsercizi(esercizio)
                        val giorno = Giorno(
                            numeroGiorno = numGiorno,
                            esercizioPerUtente = esercizio
                        )
                        giornoDao.insertGiorno(giorno)
                    }

                    it.findNavController().popBackStack()
                }else{
                    Toast.makeText(requireContext(),"Riprova", Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(requireContext(), "Aggiungere degli esercizi per poter Salvare la Giornata", Toast.LENGTH_SHORT).show()

            }

        }
        return root

    }

}