package com.example.composecounter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CountViewModel:ViewModel() {

    private var _count = MutableLiveData(0)
    val count : LiveData<Int> = _count

    fun countUp(){
        _count.value = _count.value?.plus(1)
    }
}

class SampleViewModel() : ViewModel(){
    private var _sampleData = MutableLiveData(0)
    val sampleData : LiveData<Int> = _sampleData
}