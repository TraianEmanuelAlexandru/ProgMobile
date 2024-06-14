package com.example.mygym.ui.schedaEsercizi.listaGiorni.listaEsercizi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mygym.Esercizio
import com.example.mygym.EsercizioPerUtente
import com.example.mygym.R
import com.example.mygym.databinding.FragmentNuovaGiornataBinding
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class NuovaGiornataFragment : Fragment() {
    private var _binding: FragmentNuovaGiornataBinding? = null
    private val binding get() = _binding!!
    private lateinit var firestore: FirebaseFirestore
    val argomentoListaToNuovaGiornata: NuovaGiornataFragmentArgs by navArgs()
    private lateinit var listaEsercizi : MutableList<Esercizio>
    private lateinit var listaEserciziPerUtente :MutableList<EsercizioPerUtente>
    private lateinit var filter: Filter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNuovaGiornataBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val emailUtente = argomentoListaToNuovaGiornata.argomentoEmailDaListaGiorniToNuovaGiornata
        val giorno = argomentoListaToNuovaGiornata.argomentoGiornoDaListaGiorniToNuovaGiornata.toString()
        firestore = FirebaseFirestore.getInstance()
        val db = firestore.collection(getString(R.string.collectionEsercizi))

        val recyclerView = binding.recyclerViewListaEsercizi
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.buttonAvviaFiltro.setOnClickListener{
            val nomeEsercizio = binding.editTextFiltroNomeEsercizio.text.toString()
            val bodyEsercizio = binding.editTextFiltroCorpoEsercizio.text.toString()
            val targetEsercizio = binding.editTextFiltroTargetEsercizio.text.toString()
            filter = setFilter(nomeEsercizio,bodyEsercizio, targetEsercizio)

            db.where(filter).get().addOnSuccessListener {
                documents->
                listaEsercizi = setListaEsercizi(documents)
                recyclerView.adapter = NuovaGiornataAdapter(listaEsercizi)
            }.addOnFailureListener {
                Toast.makeText(requireContext(), "Exception $it occurred", Toast.LENGTH_SHORT).show()
            }
        }
        binding.buttonConfermaListaEsercizi.setOnClickListener{
            if (listaEserciziPerUtente.isEmpty()){
                Toast.makeText(requireContext(), "Aggiungere degli esercizi per poter Salvare la Giornata", Toast.LENGTH_SHORT).show()
            }else{
                listaEserciziPerUtente = NuovaGiornataAdapter(listaEsercizi).listaEserciziPerUtente
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