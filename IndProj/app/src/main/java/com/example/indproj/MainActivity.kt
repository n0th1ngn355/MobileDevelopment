package com.example.indproj

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.indproj.data.Product
import com.example.indproj.fragments.CategoriesFragment
import com.example.indproj.fragments.ProductFragment
import com.example.lab3app.fragments.UpdateActivity

class MainActivity : AppCompatActivity(), UpdateActivity {

    interface Edit {
        fun append()
        fun update()
        fun delete()
    }

    companion object {
        const val categoryID = 0
        const val productID = 1
    }

    private var _miNewCategory: MenuItem? = null
    private var _miUpdateCategory: MenuItem? = null
    private var _miDeleteCategory: MenuItem? = null

    private var currentFragmentID = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onBackPressedDispatcher.addCallback(this){
            if(supportFragmentManager.backStackEntryCount > 0){
                supportFragmentManager.popBackStack()
                when(currentFragmentID){
                    categoryID ->{
                        finish()
                    }
                    productID->{
                        currentFragmentID= categoryID
                        setTitle("Категории")
                    }
                    else ->{}
                }
                updateMenuView()
            }
            else {
                finish()
            }
        }
        setFragment(categoryID)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        _miNewCategory = menu?.findItem(R.id.miNewCategory)
        _miUpdateCategory = menu?.findItem(R.id.miUpdateCategory)
        _miDeleteCategory = menu?.findItem(R.id.miDeleteCategory)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.miNewCategory -> {
                val fedit: Edit = CategoriesFragment.getInstance()
                fedit.append()
                true
            }

            R.id.miUpdateCategory -> {
                val fedit: Edit = CategoriesFragment.getInstance()
                fedit.update()
                true
            }

            R.id.miDeleteCategory -> {
                val fedit: Edit = CategoriesFragment.getInstance()
                fedit.delete()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun setTitle(_title: String) {
        title = _title
    }

    override fun setFragment(fragmentID: Int, product: Product?) {
        currentFragmentID = fragmentID
        when (fragmentID) {
            categoryID -> setFragment(CategoriesFragment.getInstance())
            productID -> setFragment(ProductFragment.newInstance(product ?: Product()))
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fcwMain, fragment).addToBackStack(null).commit()
        updateMenuView()
    }

    private fun updateMenuView(){
        _miNewCategory?.isVisible=(currentFragmentID== categoryID)
        _miDeleteCategory?.isVisible=(currentFragmentID== categoryID)
        _miUpdateCategory?.isVisible=(currentFragmentID== categoryID)
    }
}
