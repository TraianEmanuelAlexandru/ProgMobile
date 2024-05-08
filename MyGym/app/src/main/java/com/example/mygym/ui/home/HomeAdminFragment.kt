package com.example.mygym.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mygym.databinding.FragmentHomeAdminBinding


class HomeAdminFragment : Fragment() {
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
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}