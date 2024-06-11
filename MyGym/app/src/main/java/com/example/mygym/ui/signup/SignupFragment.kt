package com.example.mygym.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.mygym.R
import com.example.mygym.Utente
import com.example.mygym.databinding.FragmentSignupBinding
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.Date


class SignupFragment : Fragment() {
    private var _binding: FragmentSignupBinding? = null
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        binding.signupButton.setOnClickListener {
            val email = binding.signupEmail.text.toString()
            val password = binding.signupPassword.text.toString()
            val rb = binding.radioGroup.checkedRadioButtonId
            if (email.isNotEmpty() && password.isNotEmpty()) {
                if (rb != -1){
                    if (email.contains("@gmail") || email.contains("@hotmail") || email.contains("@libero") || email.contains("@mail")) {
                        var durataIscrizione = buttonChoice(rb)
                        firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        context,
                                        "Utente aggiunto correttamente",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    val dataFineIscrizione = LocalDate.now().atStartOfDay().plusMonths(durataIscrizione.toLong()).toInstant(ZoneOffset.UTC)
                                    val utente = Utente(
                                        email,
                                        Timestamp.now(),
                                        Timestamp(dataFineIscrizione),
                                        false
                                    )
                                    firestore.collection(getString(R.string.collectionUtenti)).document(email).set(utente)
                                    binding.signupEmail.text.clear()
                                    binding.signupPassword.text.clear()
                                    binding.radioGroup.clearCheck()
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Errore nell'aggiunta utente",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    } else {
                        Toast.makeText(context, "Email non valida", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Scegli la durata dell'iscrizione!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Inserisci email e password", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


//funzione per la gestione del radio group che ritorna la durata dell'iscrizione
fun buttonChoice(radiobuttonId: Int): Int{
    var result: Int
    var rb_3month = binding.rb3month.id
    var rb_4month = binding.rb4month.id
    var rb_6month = binding.rb6month.id
    when(radiobuttonId){
        rb_3month-> result = 3
        rb_4month-> result = 4
        rb_6month-> result = 6
        else-> result= 12
    }
    return result
}
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


