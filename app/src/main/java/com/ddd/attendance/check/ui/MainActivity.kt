package com.ddd.attendance.check.ui

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.ddd.attendance.check.R
import com.ddd.attendance.check.base.DDDActivity
import com.ddd.attendance.check.databinding.ActivityMainBinding
import com.ddd.attendance.check.utill.lazyThreadSafetyNone
import com.ddd.attendance.check.viewModel.MainViewModel
import javax.inject.Inject

class MainActivity : DDDActivity<ActivityMainBinding>() {
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    val viewModel by lazyThreadSafetyNone { ViewModelProviders.of(this, factory)[MainViewModel::class.java] }

    override val layoutResource: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDataBinding()
    }

    private fun setDataBinding() {
        viewDataBinding.apply {
            viewModel = this.viewModel
            setLifecycleOwner(this@MainActivity)
        }
    }
}
