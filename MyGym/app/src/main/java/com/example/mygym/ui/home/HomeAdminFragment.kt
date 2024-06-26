package com.example.mygym.ui.home

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.mygym.R
import com.example.mygym.databinding.FragmentHomeAdminBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import android.net.http.CallbackException
import androidx.activity.addCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mygym.Valutazione

class HomeAdminFragment : Fragment()  {
    private var _binding: FragmentHomeAdminBinding? = null
    private lateinit var firestore : FirebaseFirestore
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeAdminBinding.inflate(inflater, container, false)
        val root: View = binding.root
        firestore = FirebaseFirestore.getInstance()
        val recyclerView = binding.recyclerViewValutazioniGiornate
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        val listaValutazioni = mutableListOf<Valutazione>()

        firestore.collection(getString(R.string.collectionValutazioniGiornate)).get().addOnSuccessListener {
            documents->
            for (doc in documents){
                val numGiornata = doc.data.get("giornata").toString().toInt()
                val valutazione = doc.data.get("rating").toString().toFloat()
                val value = Valutazione(
                    email = doc.data.get("utente").toString() ,
                    giornata = numGiornata + 1,
                    recensione = doc.data.get("recensione").toString(),
                    valutazione = valutazione
                )
                listaValutazioni.add(value)
            }
            recyclerView.adapter = ListaValutazioniAdapter(listaValutazioni as List<Valutazione>)
        }

        val floatingActionButton: FloatingActionButton = binding.floatingActionButton
        floatingActionButton.setOnClickListener {

            view?.findNavController()!!.navigate(R.id.action_navigation_home_admin_to_signupFragment)

        }

        val qrCodeScanButton = binding.buttonScanQrCode
        qrCodeScanButton.setOnClickListener{
            if (checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED ){
                val intent = Intent("com.google.zxing.client.android.SCAN")
                register.launch(intent)
            }else{
                richiestaPermessoCamera()
            }
        }


        return root
    }
    val register = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val verifyEmail = it.data?.getStringExtra("SCAN_RESULT")
        if (verifyEmail != null) {
            //Prendo l'utente dal database
            firestore.collection(getString(R.string.collectionUtenti))
                .document(verifyEmail).get()
                .addOnSuccessListener { document ->
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
                        firestore.collection("Utenti").document(verifyEmail)
                            .update("presente", false)
                    } else {
                        Log.d("QRCODESCANNER", "utente dentro")

                        Snackbar.make(
                            binding.root,
                            "ACCESSO CONSENTITO - ISCRIZIONE VALIDA",
                            Snackbar.LENGTH_LONG
                        ).show()
                        firestore.collection("Utenti").document(verifyEmail)
                            .update("presente", true)
                    }
                }.addOnFailureListener {
                    Snackbar.make(
                        binding.root,
                        "ACCESSO NEGATO - EMAIL NON VALIDA",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
        }else{
            Toast.makeText(requireContext(), "Nessuna Email Trovata", Toast.LENGTH_SHORT).show()
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Snackbar.make(binding.root,"Accesso Camera consentito", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(binding.root,"Accesso Camera non consentito", Snackbar.LENGTH_SHORT).show()
            }
        }

    private fun richiestaPermessoCamera() {
        val permesso = android.Manifest.permission.CAMERA
        requestPermissionLauncher.launch(permesso)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}