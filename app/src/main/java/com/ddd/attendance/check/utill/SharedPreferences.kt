package com.ddd.attendance.check.utill

import android.content.Context
import android.content.Context.MODE_PRIVATE


object SharedPreferences {

    fun saveStatus(context: Context, isStatus: Boolean) {
        val pref = context.getSharedPreferences(NAME_SHARED, MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean(KEY_STATUS, isStatus)
        editor.apply()
    }

    fun getStatus(context: Context): Boolean {
        val pref = context.getSharedPreferences(NAME_SHARED, MODE_PRIVATE)
        return pref.getBoolean(KEY_STATUS, false)
    }

    private const val KEY_STATUS = "status"
    private const val NAME_SHARED = "ddd_admin_status"
}