package com.example.indproj.fragments

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Update
import com.example.indproj.MainActivity
import com.example.indproj.R
import com.example.indproj.data.Category
import com.example.indproj.data.Product
import com.example.indproj.databinding.FragmentProductListBinding
import com.example.lab3app.fragments.UpdateActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProductListFragment : Fragment() {

    private lateinit var category: Category

    companion object {
        fun newInstance(category: Category): ProductListFragment {
            return ProductListFragment().apply { this.category=category }
        }
    }

    private lateinit var viewModel: ProductListViewModel

    private lateinit var _binding : FragmentProductListBinding
    val binding
        get()=_binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding=FragmentProductListBinding.inflate(inflater, container, false)
        binding.rvProducts.layoutManager=
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductListViewModel::class.java)
        viewModel.set_Category(category)
        viewModel.productList.observe(viewLifecycleOwner){
            binding.rvProducts.adapter = ProductAdapter(it)
        }
        binding.fabNewProduct.setOnClickListener{
            viewModel.isNew = true
            editProduct(Product().apply { categoryId = viewModel.category!!.id })
//            editProduct(null)
        }
    }

    private fun deleteDialog(){
        AlertDialog.Builder(requireContext())
            .setTitle("Удаление!")
            .setMessage("Вы действительно хотите удалить ${viewModel.product?.name ?: ""}?")
            .setPositiveButton("Да") {_, _ ->
                viewModel.deleteProduct()
            }
            .setNegativeButton("Нет", null)

            .setCancelable(true)
            .create()
            .show()
    }

    private fun editProduct(product: Product?){
        (requireActivity() as UpdateActivity).setFragment(MainActivity.productID, product)
        (requireActivity() as UpdateActivity).setTitle("Категория: ${viewModel.category!!.name}")
    }

    private inner class ProductAdapter(private val items: List<Product>)
        :RecyclerView.Adapter<ProductAdapter.ItemHolder>(){
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ProductAdapter.ItemHolder {
            val view = layoutInflater.inflate(R.layout.element_product_list, parent, false)
            return ItemHolder(view)
        }

        override fun getItemCount(): Int = items.size
        override fun onBindViewHolder(holder: ProductAdapter.ItemHolder, position: Int) {
            holder.bind(viewModel.productList.value!![position])
        }
        private var lastView: View? = null
        private fun updateCurrentView(view: View){
//            val ll=lastView?.findViewById<LinearLayout>(R.id.llProductButtons)
//            ll?.visibility=View.INVISIBLE
//            ll?.layoutParams=ll?.layoutParams.apply { this?.width=1 }
//            val ib = lastView?.findViewById<ImageButton>(R.id.ibCall)
//            ib?.visibility=View.INVISIBLE
//            ib?.layoutParams=ib?.layoutParams.apply { this?.width=1 }

            lastView?.findViewById<ConstraintLayout>(R.id.clProductElement)?.setBackgroundColor(
                ContextCompat.getColor(requireContext(), R.color.white))
            view.findViewById<ConstraintLayout>(R.id.clProductElement).setBackgroundColor(
                ContextCompat.getColor(requireContext(), androidx.appcompat.R.color.material_blue_grey_800))
            lastView=view
        }

        private inner class ItemHolder(view: View):RecyclerView.ViewHolder(view){
            private lateinit var product: Product

            @OptIn(DelicateCoroutinesApi::class)
            fun bind(product: Product){
                this.product=product
                if (product==viewModel.product)
                    updateCurrentView(itemView)
                val tv=itemView.findViewById<TextView>(R.id.tvProductName)
                tv.text=product.name
                val cl=itemView.findViewById<ConstraintLayout>(R.id.clProductElement)
                cl.setOnClickListener {
                    viewModel.setCurrentProduct(product)
                    updateCurrentView(itemView)
                }
//                itemView.findViewById<ImageButton>(R.id.ibEditProduct).setOnClickListener{
//                    viewModel.isNew = false
//                    editProduct(product)
//                }
//
//                itemView.findViewById<ImageButton>(R.id.ibDeleteProduct).setOnClickListener{
//                    deleteDialog()
//                }
//
//                val llb = itemView.findViewById<LinearLayout>(R.id.llProductButtons)
//                llb.visibility=View.INVISIBLE
//                llb?.layoutParams=llb?.layoutParams.apply { this!!.width=1 }
//                val ib=itemView.findViewById<ImageButton>(R.id.ibCall)
//                ib.visibility=View.INVISIBLE
//                cl.setOnLongClickListener{
//                    cl.callOnClick()
//                    llb.visibility=View.VISIBLE
//                    if (product.phone.isNotBlank())
//                        ib.visibility=View.VISIBLE
//                    MainScope().launch {
//                        val lp=llb?.layoutParams
//                        lp?.width=1
//                        val ip = ib.layoutParams
//                        ip.width=1
//                        while (lp?.width!! < 350){
//                            lp?.width=lp?.width!!+35
//                            llb?.layoutParams=lp
//                            ip.width=ip.width+10
//                            if(ib.visibility==View.VISIBLE)
//                                ib.layoutParams=ip
//                            delay(50)
//                        }
//                    }
//                    true
//                }
//                itemView.findViewById<ImageButton>(R.id.ibCall).setOnClickListener{
//                    if(ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CALL_PHONE)==PackageManager.PERMISSION_GRANTED){
//                        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:${product.phone}"))
//                        startActivity(intent)
//                    }else{
//                        ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CALL_PHONE), 2)
//                    }
//                }
            }

        }

    }
}