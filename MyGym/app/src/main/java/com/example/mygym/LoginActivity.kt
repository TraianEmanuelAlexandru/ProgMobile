package com.example.mygym

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mygym.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()


        if(isConnected(this)) {
            binding.loginOfflineButton.visibility = View.INVISIBLE
            binding.loginButton.visibility = View.VISIBLE
            binding.loginButton.setOnClickListener {
                val email = binding.loginEmail.text.toString()
                val password = binding.loginPassword.text.toString()

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, MainActivity::class.java)
                                intent.putExtra("Email", email)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show()
                }
            }
        }else {
            binding.loginButton.visibility = View.INVISIBLE
            binding.loginOfflineButton.visibility = View.VISIBLE
            binding.loginOfflineButton.setOnClickListener {

                val email = binding.loginEmail.text.toString()
                val password = binding.loginPassword.text.toString()

                if (email.isNotEmpty() && password.isNotEmpty()) {

                    if (adminsList.any { it.email == email }) {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("Email", email)
                        startActivity(intent)
                        finish()
                    }
                }else {
                    Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.signupRedirect.setOnClickListener{
            startActivity(Intent(this, SignupActivity::class.java))
            finish()
        }
    }
}
fun isConnected(context: Context): Boolean{
    val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    if (cm != null){
        val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
        if(capabilities != null){
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)){
                Log.i("Internet", "NetworkCapabilities.TransportCellular")
                return true
            }else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
                Log.i("Internet", "NetworkCapabilities.WiFi")
                return true
            }
        }
    }
    return false
}
/*
*  if (adminsList.any { it.email == email }) {
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this, "Non sei un admin", Toast.LENGTH_SHORT).show()
                            }
* */