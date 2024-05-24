package com.example.mygym.ui.schedaEsercizi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mygym.databinding.FragmentSchedaEserciziBinding

class SchedaEserciziFragment : Fragment() {

    private var _binding: FragmentSchedaEserciziBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val schedaEserciziViewModel =
            ViewModelProvider(this).get(SchedaEserciziViewModel::class.java)

        _binding = FragmentSchedaEserciziBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        schedaEserciziViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}