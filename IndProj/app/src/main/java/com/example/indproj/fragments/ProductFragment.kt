package com.example.indproj.fragments

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.indproj.R
import com.example.indproj.data.Product
import com.example.indproj.databinding.FragmentProductBinding
import com.example.indproj.repository.ProjRepository
import com.example.indproj.repository.TAG
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat

private const val ARG_PARAM1 = "com.example.app3_352_2024.product_param"
class ProductFragment : Fragment() {

    private lateinit var product: Product
    private lateinit var viewModel: ProductListViewModel
    private lateinit var _binding: FragmentProductBinding

    val binding
        get()=_binding


//    private lateinit var viewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val param1 = it.getString(ARG_PARAM1)
            if(param1 == null)
                product = Product()
            else{
                val paramType = object : TypeToken<Product>(){}.type
                product = Gson().fromJson<Product>(param1, paramType)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentProductBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this).get(ProductListViewModel::class.java)

//        val sexArray = resources.getStringArray(R.array.SEX)
//        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, sexArray)
//        binding.spSex.adapter=adapter
//        binding.spSex.setSelection(product.sex)
//        binding.spSex.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                product.sex=position
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//                TODO("Not yet implemented")
//            }
//        }
//        binding.cvBirthDate.setOnDateChangeListener{
//                view,year,month,dayOfMonth->
//            product.birthDate.time=
//                SimpleDateFormat("yyyy.MM.dd").parse("$year.$month.$dayOfMonth")?.time ?: product.birthDate.time
//        }
//        binding.etName.setText(product.firstName)
//        binding.etLastname.setText(product.lastName)
//        binding.etMiddlename.setText(product.middleName)
//        binding.etPhone.setText(product.phone)
//        binding.cvBirthDate.date=product.birthDate.time
//        binding.btnCancel.setOnClickListener{
//            requireActivity().onBackPressedDispatcher.onBackPressed()
//        }
//        binding.btnSave.setOnClickListener{
//            product.lastName=binding.etLastname.text.toString()
//            product.firstName=binding.etName.text.toString()
//            product.middleName=binding.etMiddlename.text.toString()
//            product.phone=_binding.etPhone.text.toString()
//            Log.d(TAG, viewModel.isNew!!.toString())
//            if (viewModel.isNew!!)
//                ProjRepository.getInstance().newProduct(product)
//            else
//                ProjRepository.getInstance().updateProduct(product)
//            requireActivity().onBackPressedDispatcher.onBackPressed()
//        }
        return  binding.root
//        return super.onCreateView(inflater, container, savedInstanceState)
    }

    companion object{
        @JvmStatic
        fun newInstance(product: Product) =
            ProductFragment().apply {
                arguments=Bundle().apply {
                    putString(ARG_PARAM1, Gson().toJson(product))
                }

            }

    }
}