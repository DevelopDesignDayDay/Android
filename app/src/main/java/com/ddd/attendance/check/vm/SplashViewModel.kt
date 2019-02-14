package com.ddd.attendance.check.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ddd.attendance.check.data.repository.UserRepository
import com.ddd.attendance.check.ui.LoginActivity
import com.ddd.attendance.check.ui.MainActivity
import com.ddd.attendance.check.utill.SingleLiveEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _startMainActivity = SingleLiveEvent<Class<MainActivity>>()
    private val _startLoginActivity = SingleLiveEvent<Class<LoginActivity>>()

    val startMainActivity: LiveData<Class<MainActivity>> get() = _startMainActivity
    val startLoginActivity: LiveData<Class<LoginActivity>> get() = _startLoginActivity

    fun checkUser() {
        GlobalScope.launch {
            try {
                delay(DELAY_TIME)
                if (!userRepository.getUsers().isNullOrEmpty()) _startMainActivity.postValue(MainActivity::class.java)
                else _startLoginActivity.postValue(LoginActivity::class.java)
            } catch (error: Throwable) {

            }
        }
    }

    companion object {
        const val DELAY_TIME: Long = 900
    }
}