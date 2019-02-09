package com.ddd.attendance.check.data.login.source

import android.app.Application
import android.content.Context
import com.ddd.attendance.check.extension.SharedPreferHelper
import com.ddd.attendance.check.extension.put
import javax.inject.Inject

class LoginLocalDataSource @Inject constructor(val application: Application) {

    fun saveToken(token: String) {
        application.getSharedPreferences(SharedPreferHelper.DATA_PREFER, Context.MODE_PRIVATE).put(SharedPreferHelper.TOKEN_KEY, token)
    }
}