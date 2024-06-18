package com.example.mygym.ui.home

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.mygym.R
import com.example.mygym.databinding.FragmentHomeBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        
        val homeViewModel : HomeViewModel by viewModels()

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel.verificaNumPersone()

        val progBar_Observer = Observer<Int>{ newValue->
            binding.progressBar.progress = newValue
            binding.numeroPersonePresenti.text = newValue.toString()
        }
        homeViewModel.progBar.observe(viewLifecycleOwner, progBar_Observer)


        val floatingActionButtonQRcode : FloatingActionButton = binding.floatingActionButtonQRcode
        floatingActionButtonQRcode.setOnClickListener{
            view?.findNavController()!!.navigate(R.id.action_fragment_home_to_fragment_entryCard)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}