package com.ddd.attendance.check.vm

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ddd.attendance.check.common.NetworkHelper
import com.ddd.attendance.check.common.NetworkHelper.SUCCESS
import com.ddd.attendance.check.data.LoginRepository
import com.ddd.attendance.check.data.UserRepository
import com.ddd.attendance.check.db.entity.User
import com.ddd.attendance.check.ui.MainActivity
import com.ddd.attendance.check.utill.SingleLiveEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _error = MutableLiveData<String>()
    private val _btnEnableLogin = MutableLiveData<Boolean>()
    private val _startActivity = SingleLiveEvent<Class<MainActivity>>()

    val editID = ObservableField<String>()
    val editPW = ObservableField<String>()
    val btnEnableLogin: LiveData<Boolean> get() = _btnEnableLogin
    val error: LiveData<String> get() = _error
    val startActivity: LiveData<Class<MainActivity>> get() = _startActivity

    fun onIDTextChanged(input: CharSequence) {
        editID.set(input.toString())
        isAvailableLoginClick()
    }

    fun onPWTextChanged(input: CharSequence) {
        editPW.set(input.toString())
        isAvailableLoginClick()
    }

    private fun isAvailableLoginClick() {
        _btnEnableLogin.value = !editID.get().isNullOrEmpty() && !editPW.get().isNullOrEmpty()
    }

    fun login() {
        GlobalScope.launch {
            try {
                val response = loginRepository.login(editID.get() ?: EMPTY_ID, editPW.get()
                        ?: EMPTY_PW)
                response.body()?.let { result ->
                    if (result.status == SUCCESS) {
                        userRepository.saveUsers(
                            User(
                                result.user.id,
                                result.user.name,
                                result.user.account,
                                result.accessToken,
                                result.refreshToken
                            )
                        )
                        _startActivity.postValue(MainActivity::class.java)
                    } else {
                        _error.postValue(result.message ?: NetworkHelper.ERROR_MSG)
                    }
                }
            } catch (throwable: Throwable) {
                _error.postValue(NetworkHelper.ERROR_MSG)
            }
        }
    }

    companion object {
        const val EMPTY_ID = ""
        const val EMPTY_PW = ""
    }
}