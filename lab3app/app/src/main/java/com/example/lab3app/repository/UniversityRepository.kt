package com.example.lab3app.repository

import androidx.lifecycle.MutableLiveData
import com.example.lab3app.data.University
import com.example.lab3app.data.UniversityList

class UniversityRepository private constructor(){
    companion object{
        private var INSTANCE: UniversityRepository?=null

        fun getInstance(): UniversityRepository{
            if(INSTANCE == null){
                INSTANCE = UniversityRepository()
            }
            return INSTANCE?:
            throw IllegalStateException("UniversityRepository: Репозиторий не инициализирован")
        }
    }

    var universityList: MutableLiveData<UniversityList?> = MutableLiveData()
    var university: MutableLiveData<University> = MutableLiveData()

    fun newUniversity(university: University){
        var listTmp = (universityList.value ?: UniversityList()).apply {
            items.add(university)
        }
        universityList.postValue(listTmp)
        setCurrentUniversity(university)
    }

    fun setCurrentUniversity(_university: University){
        university.postValue(_university)
    }
    fun setCurrentUniversity(position: Int){
        if (universityList.value == null || position < 0  || (universityList.value?.items?.size!! <= position))
            return
        setCurrentUniversity(universityList.value?.items!![position])
    }

    fun getUniversityPosition(university: University):Int = universityList.value?.items?.indexOfFirst{
        it.id==university.id} ?: 1

    fun getUniversityPosition()=getUniversityPosition(university.value?: University())

    fun updateUniversity(university: University){
        val position = getUniversityPosition(university)
        if(position < 0) newUniversity(university)
        else{
            val listTmp = universityList.value!!
            listTmp.items[position] = university
            universityList.postValue(listTmp)
        }
    }

    fun deleteUniversity(university: University){
        val listTmp = universityList.value!!
        if(listTmp.items.remove(university)){
            universityList.postValue(listTmp)
        }
        setCurrentUniversity(0)
    }
}