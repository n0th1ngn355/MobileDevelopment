package com.example.indproj.fragments

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.example.indproj.MainActivity
import com.example.indproj.R
import com.example.indproj.data.Category
import com.example.indproj.databinding.FragmentCategoriesBinding
import com.example.indproj.repository.ProjRepository
import com.example.indproj.repository.TAG
import com.example.lab3app.fragments.UpdateActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CategoriesFragment : Fragment(), MainActivity.Edit {

    companion object {
        private var INSTANCE: CategoriesFragment? = null

        fun getInstance(): CategoriesFragment {
            if (INSTANCE == null) INSTANCE = CategoriesFragment()
            Log.d(TAG, "СУКА ЕБАНАЯ")
            return INSTANCE ?: throw Exception("CategoriesFragment не создан")
        }
    }

    private lateinit var viewModel: CategoriesViewModel
    private  var tabPosition: Int=0
    private lateinit var _binding: FragmentCategoriesBinding
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ProjRepository.getInstance().fetchProduct()
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[CategoriesViewModel::class.java]
        val ma = (requireActivity() as UpdateActivity)
        ma.setTitle("Категории")

        viewModel.categoryList.observe(viewLifecycleOwner){
            createUI(it)
        }
    }

    private inner class CategoryPageAdapter(fa: FragmentActivity, private val categories: List<Category>?): FragmentStateAdapter(fa){
        override fun getItemCount(): Int {
            return (categories?.size ?: 0)
        }

        override fun createFragment(position: Int): Fragment {
            return ProductListFragment.newInstance(categories!![position])
        }
    }

    private fun createUI(categoryList: List<Category>){
        binding.tlCategories.clearOnTabSelectedListeners()
        binding.tlCategories.removeAllTabs()

        for (i in 0 until  (categoryList.size)){
            binding.tlCategories.addTab(binding.tlCategories.newTab().apply {
                text= categoryList.get(i).name
            })
        }

        val adapter=CategoryPageAdapter(requireActivity(), viewModel.categoryList.value)
        binding.vpProducts.adapter=adapter
        TabLayoutMediator(binding.tlCategories, binding.vpProducts, true, true){
                tab, pos -> tab.text = categoryList.get(pos).name
        }.attach()

        tabPosition = 0
        if (viewModel.category != null)
            tabPosition = if(viewModel.getCategoryListPosition>=0)
                viewModel.getCategoryListPosition
            else
                0
        viewModel.setCurrentCategory(tabPosition)
        binding.tlCategories.selectTab(binding.tlCategories.getTabAt(tabPosition), true)

        binding.tlCategories.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tabPosition = tab?.position!!
                viewModel.setCurrentCategory(categoryList[tabPosition])
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    override fun append() {
        val mDialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_edit, null)
        val inputName = mDialogView.findViewById<EditText>(R.id.etName)
        AlertDialog.Builder(requireContext())
            .setTitle("Информация о категории ")
            .setView(mDialogView)
            .setPositiveButton("Добавить") { _, _ ->
                if (inputName.text.isNotBlank()) {
                    viewModel.appendCategory(inputName.text.toString())
                }
            }
            .setNegativeButton("Отмена", null)
            .setCancelable(true)
            .create()
            .show()
    }

    override fun update() {
        val mDialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_edit, null)
        val inputName = mDialogView.findViewById<EditText>(R.id.etName)
        inputName.setText(viewModel.category?.name ?: "")
        AlertDialog.Builder(requireContext())
            .setTitle("Изменить информацию о категории")
            .setView(mDialogView)
            .setPositiveButton("Изменить") { _, _ ->
                if (inputName.text.isNotBlank()) {
                    viewModel.updateCategory(inputName.text.toString())
                }
            }
            .setNegativeButton("Отмена", null)
            .setCancelable(true)
            .create()
            .show()
    }

    override fun delete() {
        deleteDialog()
    }

    private fun deleteDialog(){
        if(viewModel.category == null) return
        AlertDialog.Builder(requireContext())
            .setTitle("Удаление!")
            .setMessage("Вы действительно хотите удалить категорию ${viewModel.category?.name ?: ""}?")
            .setPositiveButton("Да") { _, _ ->
                viewModel.deleteCategory()
            }
            .setNegativeButton("Нет", null)
            .setCancelable(true)
            .create()
            .show()
    }

}