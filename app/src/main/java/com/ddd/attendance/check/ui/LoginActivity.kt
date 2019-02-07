package com.ddd.attendance.check.ui

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.ddd.attendance.check.R
import com.ddd.attendance.check.base.DDDActivity
import com.ddd.attendance.check.databinding.ActivityLoginBinding
import com.ddd.attendance.check.utill.lazyThreadSafetyNone
import com.ddd.attendance.check.vm.LoginViewModel

class LoginActivity : DDDActivity<ActivityLoginBinding, LoginViewModel>() {

    override val layoutResource: Int = R.layout.activity_login
    override val viewModel: LoginViewModel by lazyThreadSafetyNone { ViewModelProviders.of(this)[LoginViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setBinding()
    }

    private fun setBinding() {
        viewDataBinding.apply {
            loginViewModel = viewModel
            setLifecycleOwner(this@LoginActivity)
        }
    }
}
