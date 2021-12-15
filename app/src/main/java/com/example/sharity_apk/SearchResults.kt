package com.example.sharity_apk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.sharity_apk.adapter.CarAdapter
import com.example.sharity_apk.databinding.SearchCarsBinding
import com.example.sharity_apk.databinding.SearchResultsBinding

class SearchResults: Fragment() {

    private var _binding: SearchResultsBinding? = null
    private lateinit var binding: SearchResultsBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SearchResultsBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = CarAdapter()
        binding.recyclerView.setHasFixedSize(true)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}