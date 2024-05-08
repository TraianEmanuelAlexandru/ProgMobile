package com.example.mygym.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mygym.adminsList

class HomeAdminViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home admin Fragment"
    }
    val text: LiveData<String> = _text
}