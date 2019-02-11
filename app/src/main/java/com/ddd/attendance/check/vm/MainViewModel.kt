package com.ddd.attendance.check.vm

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ddd.attendance.check.common.UserType
import com.ddd.attendance.check.data.UserRepository
import com.ddd.attendance.check.utill.SingleLiveEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {


    private val _isAdmin = MutableLiveData<Boolean>()
    private val _btnEnableLogin = MutableLiveData<Boolean>()

    val isAdmin: LiveData<Boolean> = _isAdmin
    val editNumberAttendance = ObservableField<String>()
    val btnEnableAttendance: LiveData<Boolean> get() = _btnEnableLogin
    val showDDDDialog = SingleLiveEvent<UserType>()

    fun onInputNumberTextChanged(input: CharSequence) {
        editNumberAttendance.set(input.toString())
        _btnEnableLogin.value = !editNumberAttendance.get().isNullOrEmpty()
    }

    fun checkUserType() {
        GlobalScope.launch {
            userRepository.getUsers()
                .map { it.type }
                .map { userType ->
                    if (userType == UserType.BASIC.ordinal) basicUserUI() else adminUserUI()
                }
        }
    }

    private fun basicUserUI() {
        _isAdmin.postValue(false)
        _btnEnableLogin.postValue(false)
    }

    private fun adminUserUI() {
        _isAdmin.postValue(true)
        _btnEnableLogin.postValue(true)
    }

    fun attendance() {

    }

    fun showDDDDialog() {

    }
}