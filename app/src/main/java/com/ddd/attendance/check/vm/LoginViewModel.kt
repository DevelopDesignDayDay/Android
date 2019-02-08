package com.ddd.attendance.check.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import com.ddd.attendance.check.data.LoginRepository
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) : ViewModel() {
    fun test() {
        Log.e("", "")
        GlobalScope.launch(Dispatchers.Default, CoroutineStart.DEFAULT, null, {
            try {
                val items = loginRepository.login("ddd1", "ddd1")
            } catch (t: Throwable) {
                Log.e("ee", t.message)
            }
        })
    }
}