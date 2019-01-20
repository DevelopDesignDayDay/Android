package com.ddd.attendance.check.ui

import android.os.Bundle
import com.ddd.attendance.check.R
import com.ddd.attendance.check.base.DDDActivity
import com.ddd.attendance.check.databinding.ActivityMainBinding

class MainActivity : DDDActivity<ActivityMainBinding>() {
    override val layoutResource: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDataBinding()
    }

    private fun setDataBinding() {
        viewDataBinding.apply {

        }
    }
}
