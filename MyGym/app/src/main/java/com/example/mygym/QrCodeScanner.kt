package com.example.mygym

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.example.mygym.databinding.FragmentQrCodeScannerBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore


class QrCodeScanner : Fragment() {
    private var _binding : FragmentQrCodeScannerBinding? = null
    private val binding get() = _binding!!
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =FragmentQrCodeScannerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val intent = Intent("com.google.zxing.client.android.SCAN")
        intent.putExtra("SCAN_MODE", "QR_CODE_MODE")
        firestore = FirebaseFirestore.getInstance()
        register.launch(intent)

        return root
    }
    val register = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        val verifyEmail = it.data?.getStringExtra("SCAN_RESULT")
        //Prendo l'utente dal database
        firestore.collection("Utenti").document(verifyEmail.toString()).get()
            .addOnSuccessListener{ document->
                val scadenza = document.getDate("dataScadenza")
                val presente = document.getBoolean("presente")!!
                if (scadenza!!.before(Timestamp.now().toDate())) {
                    Log.d("QRCODESCANNER", "utente fuori")
                    Snackbar.make(
                        binding.root,
                        "ACCESSO NEGATO - ISCRIZIONE SCADUTA",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                /// Terza verifica----Utente non già all'interno della palestra
                else if (presente) {
                    Log.d("QRCODESCANNER", "utente in uscita")

                    Snackbar.make(
                        binding.root,
                        "USCITA CONSENTITA - GRAZIE E ARRIVEDERCI",
                        Snackbar.LENGTH_LONG
                    ).show()
                    //Quarta verifica---- Accesso Consentito e segnalazione presenza
                    firestore.collection("Utenti").document(verifyEmail.toString()).update("presente", false)
                } else {
                    Log.d("QRCODESCANNER", "utente dentro")

                    Snackbar.make(
                        binding.root,
                        "ACCESSO CONSENTITO - ISCRIZIONE VALIDA",
                        Snackbar.LENGTH_LONG
                    ).show()
                    firestore.collection("Utenti").document(verifyEmail.toString()).update("presente", true)
                }
            }.addOnFailureListener{
                Snackbar.make(
                    binding.root,
                    "ACCESSO NEGATO - EMAIL NON VALIDA",
                    Snackbar.LENGTH_LONG
                ).show()
            }
    }
}