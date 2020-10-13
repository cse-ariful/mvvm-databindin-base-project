package com.inverse.photoeditor.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ImagePickerViewModel : ViewModel() {
    val title = MutableLiveData<String>("Images")
}