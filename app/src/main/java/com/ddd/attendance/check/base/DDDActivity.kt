package com.ddd.attendance.check.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import dagger.android.support.DaggerAppCompatActivity

abstract class DDDActivity<T : ViewDataBinding, R : ViewModel> : DaggerAppCompatActivity() {
    abstract val layoutResource: Int
    abstract val viewModel: R
    lateinit var viewDataBinding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, layoutResource)
    }
}