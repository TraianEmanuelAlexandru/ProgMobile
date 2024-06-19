package com.example.mygym

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toolbar
import com.example.mygym.databinding.AdminActivityMainBinding
import com.example.mygym.databinding.FragmentImpostazioniBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class ImpostazioniFragment : Fragment() {
    private var _binding: FragmentImpostazioniBinding? = null
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImpostazioniBinding.inflate(inflater, container, false)
        val root: View = binding.root
        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        binding.nuovoAdminButton.setOnClickListener {
            val email = binding.nuovoAdminEmail.text.toString()
            val password = binding.nuovoAdminPassword.text.toString()
            adminsList.add(Admin(email))
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(requireContext(), "Amministratore Aggiunto Correttamente", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(requireContext(), "Errore nella connessione", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            binding.nuovoAdminEmail.text.clear()
            binding.nuovoAdminPassword.text.clear()
        }


        return root
    }

}