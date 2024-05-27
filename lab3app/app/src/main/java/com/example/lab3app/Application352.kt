package com.example.lab3app

import android.app.Application
import android.content.Context
import com.example.lab3app.repository.UniversityRepository

class ApplicationInd: Application() {
    override fun onCreate() {
        super.onCreate()
        UniversityRepository.getInstance().loadData()
    }


    init {
        instance  = this
    }

    companion object{
        private var instance: ApplicationInd? = null
        val context
            get() = applicationContext()

        private fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}