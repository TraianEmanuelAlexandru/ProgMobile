package com.example.mygym.ui.signup

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import com.example.mygym.LoginActivity
import com.example.mygym.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth




class SignupFragment : Fragment() {
    private var _binding: FragmentSignupBinding? = null
    private lateinit var firebaseAuth: FirebaseAuth
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
        binding.signupButton.setOnClickListener{
            val nome = binding.signupNome.text.toString()
            val cognome = binding.signupCognome.text.toString()
            val email = binding.signupEmail.text.toString()
            val password = binding.signupPassword.text.toString()

            if (nome.isNotEmpty() && cognome.isNotEmpty() ) {
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    if(email.contains("@gmail") && email.contains("@hotmail") && email.contains("@libero") && email.contains("@mail")) {
                        firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        context,
                                        "Utente aggiunto correttamente",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                } else {
                                    Toast.makeText(
                                        context,
                                        "Errore nell'aggiunta utente",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    }else{
                        Toast.makeText(context, "Email non valida", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Enter email and password", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(context, "Enter nome and cognome", Toast.LENGTH_SHORT).show()
            }
        }



    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


