package com.example.lab3app.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab3app.R
import com.example.lab3app.databinding.FragmentFacultyListBinding
import com.example.lab3app.databinding.FragmentUniversityListBinding

class FacultyListFragment : Fragment() {

    companion object {
        private var INSTANCE: FacultyListFragment? = null

        fun getInstance(): FacultyListFragment {
            if (INSTANCE == null) INSTANCE = FacultyListFragment()
            return INSTANCE ?: throw Exception("FacultyListFragment не создан")
        }
    }

    private lateinit var viewModel: FacultyListViewModel
    private lateinit var _binding: FragmentFacultyListBinding
    val binding
        get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFacultyListBinding.inflate(inflater, container, false)
        binding.rvFacultyList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(FacultyListViewModel::class.java)
        viewModel.facultyList.observe(viewLifecycleOwner) {
            binding.rvFacultyList.adapter = FacultyAdapter(it!!.items)
        }
        binding.floatingActionButton.setOnClickListener{
            newFaculty()
        }
    }
}