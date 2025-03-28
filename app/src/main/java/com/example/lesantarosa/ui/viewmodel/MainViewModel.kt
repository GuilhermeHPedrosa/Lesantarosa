package com.example.lesantarosa.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lesantarosa.models.data.VisualComponents

class MainViewModel: ViewModel() {

    private val _visualComponents: MutableLiveData<VisualComponents> = MutableLiveData(VisualComponents())
    val visualComponents: LiveData<VisualComponents> get() = _visualComponents

    fun defineVisualComponents(visualComponents: VisualComponents) {
        _visualComponents.postValue(visualComponents)
    }
}