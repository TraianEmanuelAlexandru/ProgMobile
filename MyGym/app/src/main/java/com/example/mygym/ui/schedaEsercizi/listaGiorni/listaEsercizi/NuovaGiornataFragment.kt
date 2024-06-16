package com.example.mygym.ui.schedaEsercizi.listaGiorni.listaEsercizi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygym.Esercizio
import com.example.mygym.EsercizioPerUtente
import com.example.mygym.R
import com.example.mygym.databinding.FragmentNuovaGiornataBinding
import com.example.mygym.ui.schedaEsercizi.listaGiorni.listaEsercizi.NuovaGiornataFragmentArgs
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.util.HashMap

class NuovaGiornataFragment : Fragment() {
    private var _binding: FragmentNuovaGiornataBinding? = null
    private val binding get() = _binding!!
    private lateinit var firestore: FirebaseFirestore
    val argomentoListaGiorniToNuovaGiornata: NuovaGiornataFragmentArgs by navArgs()
    private lateinit var filter: Filter
    private lateinit var listaEserciziPerGiorno: HashMap<String,EsercizioPerUtente>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNuovaGiornataBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val emailUtente = argomentoListaGiorniToNuovaGiornata.argomentoEmailDaListaGiorniToNuovaGiornata
        firestore = FirebaseFirestore.getInstance()
        val dbRefEsercizi = firestore.collection(getString(R.string.collectionEsercizi))
        val dbRefUtente = firestore.collection(getString(R.string.collectionUtenti)).document(emailUtente)

        val recyclerView = binding.recyclerViewListaEsercizi
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val listaEserciziPerUtente  = mutableListOf<EsercizioPerUtente>()

        binding.buttonAvviaFiltro.setOnClickListener{
            val nomeEsercizio = binding.editTextFiltroNomeEsercizio.text.toString()
            val bodyEsercizio = binding.editTextFiltroCorpoEsercizio.text.toString()
            val targetEsercizio = binding.editTextFiltroTargetEsercizio.text.toString()
            filter = setFilter(nomeEsercizio,bodyEsercizio, targetEsercizio)

            dbRefEsercizi.where(filter).get().addOnSuccessListener {
                documents->
                var listaEsercizi = setListaEsercizi(documents)
                recyclerView.adapter = NuovaGiornataAdapter(listaEsercizi, listaEserciziPerUtente)
            }.addOnFailureListener {
                Toast.makeText(requireContext(), "Exception $it occurred", Toast.LENGTH_SHORT).show()
            }
        }
        binding.buttonConfermaListaEsercizi.setOnClickListener{
            if (listaEserciziPerUtente.isNotEmpty()){
                //val lista = mutableListOf<HashMap<String,Any>>()
                var i = 0
                listaEserciziPerGiorno = hashMapOf()
                for (esercizio in listaEserciziPerUtente) {
                    i++
                    listaEserciziPerGiorno.put(
                      "$i", esercizio
                    )
                }
                //listaEserciziPerGiorno = hashMapOf("listaEsercizi" to lista)

                dbRefUtente.collection(getString(R.string.collectionEserciziPerGiorno))
                    .add(listaEserciziPerGiorno)
                    .addOnSuccessListener {
                        Log.d(
                            "DB",
                            "Giorno inserito per utente $emailUtente-------------------------------------------------------------------"
                        )
                    }.addOnFailureListener {
                        Log.d(
                            "TAG",
                            "Exception $it occurred-------------------------------------------------------------------------------------------"
                        )
                    }
                it.findNavController().popBackStack()
            }else{
                Toast.makeText(requireContext(), "Aggiungere degli esercizi per poter Salvare la Giornata", Toast.LENGTH_SHORT).show()

            }

        }
        return root
    }

    private fun setFilter(nomeEsercizio: String, bodyEsercizio: String, targetEsercizio: String) : Filter {
        if (nomeEsercizio.isEmpty()){
            if(bodyEsercizio.isEmpty()) {
                if (targetEsercizio.isEmpty()) {
                    filter = Filter.and(
                        Filter.greaterThanOrEqualTo("name", "a"),
                        Filter.lessThan("name", "z")
                    )
                }else{
                    filter = Filter.and(
                        Filter.greaterThanOrEqualTo("target", targetEsercizio),
                        Filter.lessThan("target", targetEsercizio + "z")
                    )
                }
            }else{
                if (targetEsercizio.isEmpty()) {
                    filter = Filter.and(
                        Filter.greaterThanOrEqualTo("bodyPart", bodyEsercizio),
                        Filter.lessThan("bodyPart", bodyEsercizio +"z")
                    )
                }else{
                    filter = Filter.and(
                        Filter.greaterThanOrEqualTo("target", targetEsercizio),
                        Filter.lessThan("target", targetEsercizio + "z"),
                        Filter.greaterThanOrEqualTo("bodyPart", bodyEsercizio),
                        Filter.lessThan("bodyPart", bodyEsercizio +"z")
                    )
                }
            }
        }else{
            if(bodyEsercizio.isEmpty()) {
                if (targetEsercizio.isEmpty()) {
                    filter = Filter.and(
                        Filter.greaterThanOrEqualTo("name", nomeEsercizio),
                        Filter.lessThan("name", nomeEsercizio +"z")
                    )
                }else{
                    filter = Filter.and(
                        Filter.greaterThanOrEqualTo("name", nomeEsercizio),
                        Filter.lessThan("name", nomeEsercizio +"z"),
                        Filter.greaterThanOrEqualTo("target", targetEsercizio),
                        Filter.lessThan("target", targetEsercizio + "z")
                    )
                }
            }else{
                if (targetEsercizio.isEmpty()) {
                    filter = Filter.and(
                        Filter.greaterThanOrEqualTo("name", nomeEsercizio),
                        Filter.lessThan("name", nomeEsercizio +"z"),
                        Filter.greaterThanOrEqualTo("bodyPart", bodyEsercizio),
                        Filter.lessThan("bodyPart", bodyEsercizio +"z")
                    )
                }else{
                    filter = Filter.and(
                        Filter.greaterThanOrEqualTo("name", nomeEsercizio),
                        Filter.lessThan("name", nomeEsercizio +"z"),
                        Filter.greaterThanOrEqualTo("target", targetEsercizio),
                        Filter.lessThan("target", targetEsercizio + "z"),
                        Filter.greaterThanOrEqualTo("bodyPart", bodyEsercizio),
                        Filter.lessThan("bodyPart", bodyEsercizio +"z")
                    )
                }
            }
        }
        return filter
    }

    private fun setListaEsercizi(documents: QuerySnapshot?): MutableList<Esercizio> {
        val listaEsercizi = mutableListOf<Esercizio>()
        for (document in documents!!){
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
        return listaEsercizi
    }
}