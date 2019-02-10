package com.ddd.attendance.check.ui

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.ddd.attendance.check.R
import com.ddd.attendance.check.base.DDDActivity
import com.ddd.attendance.check.databinding.ActivitySplashBinding
import com.ddd.attendance.check.utill.lazyThreadSafetyNone
import com.ddd.attendance.check.vm.SplashViewModel
import javax.inject.Inject

class SplashActivity : DDDActivity<ActivitySplashBinding, SplashViewModel>() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    override val viewModel: SplashViewModel by lazyThreadSafetyNone {
        ViewModelProviders.of(this, factory)[SplashViewModel::class.java]
    }
    override val layoutResource: Int = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDataBinding()
        viewModel.checkUser()

        viewModel.startLoginActivity.observe(this, Observer {
            startActivity(Intent(this, it))
            finish()
        })

        viewModel.startMainActivity.observe(this, Observer {
            startActivity(Intent(this, it))
            finish()
        })


    }

    private fun setDataBinding() {
        viewDataBinding.apply {
            splashViewModel = viewModel
            setLifecycleOwner(this@SplashActivity)
        }
    }
}
