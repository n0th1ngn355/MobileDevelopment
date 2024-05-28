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

    var flag : Boolean = true
    val binding
        get()=_binding

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentProductBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this).get(ProductListViewModel::class.java)

        binding.etProductName.setText(product.name)
        binding.etDescription.setText(product.description ?: "")
        binding.etPrice.setText(product.price.toString())
        binding.etColor.setText(product.color)
        binding.etSizes.setText(product.sizesAvailable)
        binding.etCountry.setText(product.country)
        binding.etStockQuantity.setText(product.stockQuantity.toString())
        binding.etManufacturer.setText(product.manufacturer)

        binding.btnCancel.setOnClickListener{
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.btnSave.setOnClickListener{
            product.name = binding.etProductName.text.toString()
            product.description = binding.etDescription.text.toString()
            product.price = binding.etPrice.text.toString().toDouble()
            product.color = binding.etColor.text.toString()
            product.sizesAvailable = binding.etSizes.text.toString()
            product.country = binding.etCountry.text.toString()
            product.stockQuantity = binding.etStockQuantity.text.toString().toInt()
            product.manufacturer = binding.etManufacturer.text.toString()
            if (flag)
                ProjRepository.getInstance().newProduct(product)
            else
                ProjRepository.getInstance().updateProduct(product)
            ProjRepository.getInstance().setCurrentProduct(product)
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        return  binding.root
//        return super.onCreateView(inflater, container, savedInstanceState)
    }

    companion object{
        @JvmStatic
        fun newInstance(product: Product, fl: Boolean) =
            ProductFragment().apply {
                flag = fl
                arguments=Bundle().apply {
                    putString(ARG_PARAM1, Gson().toJson(product))
                }

            }

    }
}