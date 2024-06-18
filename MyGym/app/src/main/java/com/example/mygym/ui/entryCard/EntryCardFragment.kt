package com.example.mygym.ui.entryCard

import android.content.Context.MODE_PRIVATE
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.mygym.R
import com.example.mygym.databinding.FragmentEntrycardBinding


class EntryCardFragment : Fragment() {
    private var _binding: FragmentEntrycardBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEntrycardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val viewModel : EntryCardViewModel by viewModels()
        val sharedPref = this.activity?.getSharedPreferences(getString(R.string.profile_key), MODE_PRIVATE)
        val data = sharedPref?.getString("email", "")

        binding.buttonGeneraQrCode.setOnClickListener{
            viewModel.generateQrCode(data)
        }

        val imageBitMap_Observer = Observer<Bitmap>{newValue->
            binding.QrCode.setImageBitmap(newValue)
        }
        viewModel.imageBitMap.observe(viewLifecycleOwner, imageBitMap_Observer)
        return root
    }

}

