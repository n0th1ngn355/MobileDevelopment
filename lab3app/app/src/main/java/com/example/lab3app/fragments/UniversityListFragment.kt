package com.example.lab3app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab3app.databinding.FragmentUniversityListBinding

class UniversityListFragment: Fragment() {
    companion object{
        private var INSTANCE: UniversityListFragment?=null

        fun getInstance(): UniversityListFragment{
            if (INSTANCE == null) INSTANCE= UniversityListFragment()
            return INSTANCE ?: throw Exception("UniversityListFragment не создан")
        }
    }

    private lateinit var viewModel: UniversityListViewModel
    private lateinit var _binding: FragmentUniversityListBinding
    val binding
        get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUniversityListBinding.inflate(inflater, container, false)
        binding.rvUniversityList.layoutManager=
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        
        return binding.root
    }
    
}