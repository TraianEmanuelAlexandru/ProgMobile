package com.example.mygym

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.example.mygym.databinding.FragmentListaEserciziBinding

class ListaEserciziFragment : Fragment() {
    private var _binding: FragmentListaEserciziBinding? = null
    private val binding get() = _binding!!

    val argomentoListaToEsercizi: ListaEserciziFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListaEserciziBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val emailUtente = argomentoListaToEsercizi.argomento
        binding.testo.text = emailUtente

        return root
    }

}