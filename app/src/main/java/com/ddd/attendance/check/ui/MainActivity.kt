package com.ddd.attendance.check.ui

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.ddd.attendance.check.R
import com.ddd.attendance.check.base.DDDActivity
import com.ddd.attendance.check.databinding.ActivityMainBinding
import com.ddd.attendance.check.utill.lazyThreadSafetyNone
import com.ddd.attendance.check.vm.MainViewModel
import javax.inject.Inject


class MainActivity : DDDActivity<ActivityMainBinding, MainViewModel>() {

    override val layoutResource: Int = R.layout.activity_main
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    override val viewModel: MainViewModel by lazyThreadSafetyNone { ViewModelProviders.of(this, factory)[MainViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDataBinding()
    }

    private fun setDataBinding() {
        viewDataBinding.apply {
            mainViewModel = viewModel
            setLifecycleOwner(this@MainActivity)
        }
    }
}
