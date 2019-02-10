package com.ddd.attendance.check.vm

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {


    private val _btnEnableLogin = MutableLiveData<Boolean>()

    val editNumberAttendance = ObservableField<String>()
    val btnEnableAttendance: LiveData<Boolean> get() = _btnEnableLogin

    fun onInputNumberTextChanged(input: CharSequence) {
        editNumberAttendance.set(input.toString())
        _btnEnableLogin.value = !editNumberAttendance.get().isNullOrEmpty()
    }

}