package com.ddd.attendance.check.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ddd.attendance.check.data.repository.UserRepository
import com.ddd.attendance.check.ui.LoginActivity
import com.ddd.attendance.check.ui.MainActivity
import com.ddd.attendance.check.utill.SingleLiveEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _result = SingleLiveEvent<Result>()
    val result: LiveData<Result> = _result

    sealed class Result {
        data class StartMainActivity(val classType: Class<MainActivity>) : Result()
        data class StartLoginActivity(val classType: Class<LoginActivity>) : Result()
    }
    fun checkUser() {
        GlobalScope.launch {
            try {
                if (!userRepository.getUsers().isNullOrEmpty()) _result.postValue(Result.StartMainActivity(MainActivity::class.java))
                else _result.postValue(Result.StartLoginActivity(LoginActivity::class.java))
            } catch (error: Throwable) {

            }
        }
    }
}