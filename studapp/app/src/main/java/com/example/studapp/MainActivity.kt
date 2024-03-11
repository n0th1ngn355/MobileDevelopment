package com.example.studapp

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.example.studapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    var st = Student("Имя", "Фамилия", 1)

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode == Activity.RESULT_OK){
            val name = result.data?.getStringExtra("name") ?: ""
            val lastname = result.data?.getStringExtra("lastname") ?: ""
            val course = result.data?.getIntExtra("course", 1) ?: 1
            st = Student(name, lastname, course)
            updateView()
        }
    }

    private fun updateView() {
        binding.nameTV.text =  "Имя: " + st.name
        binding.lastnameTV.text =  "Фамилия: " + st.lastname
        binding.courseTV.text =  "Курс: " + st.course.toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addBtn.setOnClickListener{
            val intent = AddEditActivity.newIntent(this@MainActivity, false)
            resultLauncher.launch(intent)
        }

        binding.editBtn.setOnClickListener{
            val intent = AddEditActivity.newIntent(this@MainActivity, true, st.name, st.lastname, st.course)
            resultLauncher.launch(intent)
        }


        updateView()
    }


}