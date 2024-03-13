package com.example.lab3app.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab3app.MainActivity
import com.example.lab3app.R
import com.example.lab3app.data.University
import com.example.lab3app.databinding.FragmentUniversityListBinding

class UniversityListFragment: Fragment(), MainActivity.Edit {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(UniversityListViewModel::class.java)
        viewModel.universityList.observe(viewLifecycleOwner){
            binding.rvUniversityList.adapter = UniversityAdapter(it!!.items)
        }
    }

    private inner class UniversityAdapter(private val items: List<University>): RecyclerView.Adapter<UniversityAdapter.ItemHolder>(){
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): UniversityAdapter.ItemHolder {
            val item = layoutInflater.inflate(R.layout.element_university_list, parent, false)
            return ItemHolder(item!!)
        }

        override fun getItemCount(): Int = items.size
        override fun onBindViewHolder(holder: UniversityAdapter.ItemHolder, position: Int) {
            holder.bind(viewModel.universityList.value!!.items[position])
        }
        private inner class ItemHolder(view: View): RecyclerView.ViewHolder(view){
            private lateinit var university: University

            fun bind(university: University){
                this.university = university
                val tv = itemView.findViewById<TextView>(R.id.tvUniversityName)
                tv.text = university.name
                val tvc = itemView.findViewById<TextView>(R.id.tvUniversityCity)
                tvc.text = university.city
            }
        }
    }

    override fun append() {
        newUniversity()
    }

    override fun delete() {
        TODO("Not yet implemented")
    }

    override fun update() {
        TODO("Not yet implemented")
    }

    private fun newUniversity(){
        val mDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_university_edit, null)
        val inputName = mDialogView.findViewById<EditText>(R.id.etName)
        val inputCity = mDialogView.findViewById<EditText>(R.id.etCity)
        AlertDialog.Builder(requireContext())
            .setTitle("Информация об университете")
            .setView(mDialogView)
            .setPositiveButton("Добавить"){ _, _ ->
                if (inputName.text.isNotBlank() and inputCity.text.isNotBlank()){
                    viewModel.appendUniversity(inputName.text.toString(), inputCity.text.toString())
                }
            }
            .setNegativeButton("Отмена", null)
            .setCancelable(true)
            .create()
            .show()
    }
}