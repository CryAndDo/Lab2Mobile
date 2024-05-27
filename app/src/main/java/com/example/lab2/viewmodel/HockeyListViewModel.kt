package com.example.lab2.viewmodel

import androidx.lifecycle.ViewModel
import com.example.lab2.HockeyRepository

class HockeyListViewModel:ViewModel() {
    private val hockeyRepository=HockeyRepository.get();
    val hockeyListLiveData=hockeyRepository.getHockeys()
}