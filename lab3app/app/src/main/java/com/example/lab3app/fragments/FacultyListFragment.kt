package com.example.lab3app.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lab3app.R

class FacultyListFragment : Fragment() {

    companion object {
        fun newInstance() = FacultyListFragment()
    }

    private lateinit var viewModel: FacultyListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_faculty_list2, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FacultyListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}