package com.example.lab2.viewmodel



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.lab2.HockeyRepository
import com.example.lab2.model.Hockey
import java.util.UUID

class HockeyViewModel : ViewModel() {
    private val hockeyRepository=HockeyRepository.get()
    private val hockeyIdLiveData=MutableLiveData<UUID>()
    var hockeyLiveData:LiveData<Hockey?> =
        hockeyIdLiveData.switchMap { hockeyId->hockeyRepository.getHockey(hockeyId) }
    fun loadHockey(hockeyID: UUID){
        hockeyIdLiveData.value=hockeyID
    }
    fun saveHockey(hockey: Hockey){
        hockeyRepository.insertHockey(hockey)
    }
    fun updateHockey(hockey: Hockey){
        hockeyRepository.updateHockey(hockey)
    }
}