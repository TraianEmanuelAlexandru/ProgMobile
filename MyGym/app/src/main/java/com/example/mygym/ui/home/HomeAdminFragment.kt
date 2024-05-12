package com.example.mygym.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.mygym.MainActivity
import com.example.mygym.R
import com.example.mygym.databinding.FragmentHomeAdminBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton


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
        val floatingActionButton: FloatingActionButton = binding.floatingActionButton
        floatingActionButton.setOnClickListener {

            view?.findNavController()!!.navigate(R.id.action_navigation_home_admin_to_signupFragment)

        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}