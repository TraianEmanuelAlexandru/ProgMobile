package com.example.mygym.ui.entryCard

import android.graphics.Bitmap
import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter

class EntryCardViewModel : ViewModel() {
private val _imageBitMap = MutableLiveData<Bitmap>()
    val imageBitMap: LiveData<Bitmap> = _imageBitMap
    fun generateQrCode(data : String?){
        val qrCode = QRCodeWriter()
        val qrCodice = qrCode.encode(data.toString(), BarcodeFormat.QR_CODE, 400, 400)
        val height = qrCodice.height
        val width = qrCodice.width
        val bitMap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0..<width){
            for (y in 0 ..<height){
                bitMap.setPixel(x, y, chooseColor(x,y,qrCodice))
            }
        }
        _imageBitMap.setValue(bitMap)
        //_imageBitMap.switchMap { bitmap -> imageBitMap }

    }
    private fun chooseColor(x: Int, y: Int, bitMatrix: BitMatrix): Int {
        if (bitMatrix.get(x,y))
            return Color.BLACK
        else
            return Color.WHITE

    }
}
