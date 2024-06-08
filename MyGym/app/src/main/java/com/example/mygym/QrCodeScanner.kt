package com.example.mygym

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.ImageAnalysis.COORDINATE_SYSTEM_VIEW_REFERENCED
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.CameraController
import androidx.core.content.ContextCompat
import com.example.mygym.databinding.FragmentQrCodeScannerBinding
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode


class QrCodeScanner : Fragment() {
    private var _binding : FragmentQrCodeScannerBinding? = null
    private val binding get() = _binding!!
    private lateinit var cameraController: CameraController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =FragmentQrCodeScannerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivity(intent)

        /*val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
        val barcodeScanner = BarcodeScanning.getClient(options)

        cameraController.setImageAnalysisAnalyzer(
            ContextCompat.getMainExecutor(requireContext()),
            MlKitAnalyzer(
                listOf(barcodeScanner),
                COORDINATE_SYSTEM_VIEW_REFERENCED,
                ContextCompat.getMainExecutor(requireContext())
            ) { result: MlKitAnalyzer.Result? ->
            }
        )*/


        return root
    }
}