package com.example.mygym

import android.content.Context.MODE_PRIVATE
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mygym.databinding.FragmentEntrycardBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.BarcodeFormat
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter


class EntryCardFragment : Fragment() {
    private var _binding: FragmentEntrycardBinding? = null
    private lateinit var firebaseAuth: FirebaseAuth

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEntrycardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = this.activity?.getPreferences( MODE_PRIVATE)
        val data = sharedPref?.getString("email", "")
        val qrCode = QRCodeWriter()
        val qrCodice = qrCode.encode(data.toString(), BarcodeFormat.QR_CODE,400,400)
        val height = qrCodice.height
        val width = qrCodice.width
        val bitMap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0..<width){
            for (y in 0 ..<height){
                bitMap.setPixel(x, y, chooseColor(x,y,qrCodice))
            }
        }
        binding.QRcode.setImageBitmap(bitMap)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
private fun chooseColor(x: Int, y: Int, bitMatrix: BitMatrix): Int {
    if (bitMatrix.get(x,y))
        return Color.BLACK
    else return Color.WHITE

}

