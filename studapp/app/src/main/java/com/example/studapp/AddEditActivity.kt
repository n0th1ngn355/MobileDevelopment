package com.example.studapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.studapp.databinding.ActivityAddEditBinding

class AddEditActivity : AppCompatActivity() {

    companion object {
        fun newIntent(packageContext: Context, edit: Boolean, name: String="", lastname: String="", course: Int=1): Intent {
            return Intent(packageContext, AddEditActivity::class.java).apply {
                putExtra("edit_mode", edit)
                putExtra("name", name)
                putExtra("lastname", lastname)
                putExtra("course", course)
            }
        }
    }

    private lateinit var binding: ActivityAddEditBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditBinding.inflate(layoutInflater)

        val isEdit = intent?.getBooleanExtra("edit_mode", false)

        setContentView(binding.root)
        if (isEdit!!) {
            binding.nameET.setText(intent.getStringExtra("name"))
            binding.lastnameET.setText(intent.getStringExtra("lastname"))
            binding.courseET.setText(intent.getIntExtra("course", 1).toString())
        }

        binding.cancelBtn.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        binding.okBtn.setOnClickListener {
            if (binding.nameET.text.isNullOrEmpty() || binding.lastnameET.text.isNullOrEmpty()){
                Toast.makeText(this, "Поле имя или фамилия ", Toast.LENGTH_SHORT).show()
            }else if (Regex("[0-9]*").matches(binding.nameET.text.toString()) || Regex("[0-9]*").matches(binding.lastnameET.text.toString())){
                Toast.makeText(this, "В полях имя или фамилия присутствуют цифры", Toast.LENGTH_SHORT).show()
            }else{
                val data = Intent().apply {
                    putExtra("name", binding.nameET.text.toString())
                    putExtra("lastname", binding.lastnameET.text.toString())
                    putExtra("course", binding.courseET.text.toString().toInt())
                }
                setResult(Activity.RESULT_OK, data)
                finish()
            }
        }
    }
}
