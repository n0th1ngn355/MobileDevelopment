package com.example.indproj


import android.app.Application
import android.content.Context
import com.example.indproj.repository.ProjRepository

class ApplicationIndiv: Application() {
    override fun onCreate() {
        super.onCreate()
        ProjRepository.getInstance().loadData()
    }


    init {
        instance  = this
    }

    companion object{
        private var instance: ApplicationIndiv? = null
        val context
            get() = applicationContext()

        private fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}