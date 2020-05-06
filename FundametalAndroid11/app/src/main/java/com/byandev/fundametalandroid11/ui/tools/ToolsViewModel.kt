package com.byandev.fundametalandroid11.ui.tools

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ToolsViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is Tools Fragment"
    }
    val text: LiveData<String> = _text
}