package com.example.mygym.ui.schedaEsercizi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.mygym.databinding.FragmentSchedeUtenteBinding
import com.google.firebase.firestore.FirebaseFirestore

class SchedeUtenteFragment : Fragment() {
    private var _binding: FragmentSchedeUtenteBinding? = null
    private val binding get() = _binding!!
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSchedeUtenteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        firestore = FirebaseFirestore.getInstance()



        binding.buttonSchedaOnline.setOnClickListener {
            val fromOnline = true
            val action = SchedeUtenteFragmentDirections.actionFragmentSchedeUtenteToFragmentListaGiorni(fromOnline)
            it.findNavController().navigate(action)
        }
        return root
    }

}