package com.example.mygym

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mygym.databinding.FragmentImpostazioniBinding
import com.example.mygym.databinding.FragmentImpostazioniUtenteBinding
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale

class ImpostazioniUtenteFragment : Fragment() {
    private var _binding: FragmentImpostazioniUtenteBinding? = null
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImpostazioniUtenteBinding.inflate(inflater, container, false)
        val root: View = binding.root
        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val sharedPref = this.activity?.getSharedPreferences(getString(R.string.profile_key),
            Context.MODE_PRIVATE
        )
        val data = sharedPref?.getString("email","")!!
        val dbRefUtente = firestore.collection(getString(R.string.collectionUtenti)).document(data)
        dbRefUtente.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val dataIscrizione = it.result.get("dataIscrizione") as Timestamp
                val dataScadenza = it.result.get("dataScadenza") as Timestamp

                val simpleDate = SimpleDateFormat("dd/M/yyyy")
                val dataIscr = simpleDate.format(dataIscrizione.toDate())
                val dataScad = simpleDate.format(dataScadenza.toDate())

                binding.dataIscrizione.text = dataIscr
                binding.dataScadenza.text = dataScad

            }
        }

        binding.cambiaCredenzialiButton.setOnClickListener {
            val email = binding.vecchiaEmail.text.toString()
            val password = binding.vecchiaPassword.text.toString()
            val nuovaEmail = binding.nuovaEmail.text.toString()
            val nuovaPassword = binding.nuovaPassword.text.toString()
            if (firebaseAuth.currentUser != null) {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            firebaseAuth.currentUser!!.verifyBeforeUpdateEmail(nuovaEmail)
                            firebaseAuth.currentUser!!.updatePassword(nuovaPassword)
                            dbRefUtente.update("emailUtente", nuovaEmail)
                            Toast.makeText(requireContext(), "Credenziali cambiate correttamente", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(requireContext(), "Login failed", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                binding.vecchiaEmail.text.clear()
                binding.vecchiaPassword.text.clear()
                binding.nuovaEmail.text.clear()
                binding.nuovaPassword.text.clear()
            }
        }


        return root
    }
    private fun getDateString(time: Int) : String = simpleDateFormat.format(time * 1000L)
}