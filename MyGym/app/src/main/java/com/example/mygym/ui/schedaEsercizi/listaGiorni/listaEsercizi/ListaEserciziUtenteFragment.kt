package com.example.mygym.ui.schedaEsercizi.listaGiorni.listaEsercizi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygym.EsercizioPerUtente
import com.example.mygym.EsercizioRoomDatabase
import com.example.mygym.Giorno
import com.example.mygym.databinding.FragmentListaEserciziBinding
import com.example.mygym.databinding.FragmentListaEserciziUtenteBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListaEserciziUtenteFragment : Fragment() {
    private var _binding: FragmentListaEserciziUtenteBinding? = null
    private val binding get() = _binding!!

    val argomentoListaGiorniUtenteToListaEserciziUtente: ListaEserciziUtenteFragmentArgs by navArgs()
    private lateinit var listaEsercizi: MutableList<EsercizioPerUtente>
    private lateinit var listaEserciziPerGiorno: List<Giorno>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentListaEserciziUtenteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.recyclerViewListaEserciziUtente
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        listaEsercizi = mutableListOf()

        val numeroGiorno = argomentoListaGiorniUtenteToListaEserciziUtente.argomentoDaListaGiorniUtenteToListaEserciziUtente
        val giornoDao = EsercizioRoomDatabase.getInstance(requireContext()).giornoDao()
            listaEserciziPerGiorno = giornoDao.getListaEserciziPerGiorno(numeroGiorno)
        for (giorno in listaEserciziPerGiorno){
            listaEsercizi.add(giorno.esercizioPerUtente)
        }
        recyclerView.adapter = ListaEserciziAdapter(listaEsercizi, false)
        return root
    }


}