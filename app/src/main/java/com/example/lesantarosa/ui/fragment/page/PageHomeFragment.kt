package com.example.lesantarosa.ui.fragment.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lesantarosa.databinding.FragmentPageHomeBinding

class PageHomeFragment: PageFragment() {

    private lateinit var binding: FragmentPageHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPageHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
}