package com.example.mygym

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mygym.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(this, "Signup successful", Toast.LENGTH_SHORT)
                                        .show()
                                    val intent = Intent(this, LoginActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(this, "Signup unsuccessful", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                    }else{
                        Toast.makeText(this, "Email not valid", Toast.LENGTH_SHORT).show()
                    }
                }else {
                    Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show()
                }
            }else {
                Toast.makeText(this, "Enter nome and cognome", Toast.LENGTH_SHORT).show()
            }
        }
    }
}