package com.example.mygym.ui.home

import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.mygym.R
import com.example.mygym.databinding.FragmentHomeAdminBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import java.util.jar.Manifest


class HomeAdminFragment : Fragment()  {
    private var _binding: FragmentHomeAdminBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeAdminViewModel =
            ViewModelProvider(this).get(HomeAdminViewModel::class.java)

        _binding = FragmentHomeAdminBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHomeAdmin
        homeAdminViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        val floatingActionButton: FloatingActionButton = binding.floatingActionButton
        floatingActionButton.setOnClickListener {

            view?.findNavController()!!.navigate(R.id.action_navigation_home_admin_to_signupFragment)

        }

        val qrCodeScanButton = binding.buttonScanQrCode
        qrCodeScanButton.setOnClickListener{
            if (checkPermissionFotocamera() ){
                view?.findNavController()!!.navigate(R.id.action_navigation_home_admin_to_signupFragment)
            }else{
            }
        }

        return root
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

    private fun checkPermissionFotocamera(): Boolean {
        if (checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED ) {
            return true
        }else{
            richiestaPermessoCamera()
            return false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}