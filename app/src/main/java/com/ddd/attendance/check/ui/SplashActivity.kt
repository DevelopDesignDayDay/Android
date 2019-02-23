package com.ddd.attendance.check.ui

import android.animation.Animator
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
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashActivity : DDDActivity<ActivitySplashBinding, SplashViewModel>(), Animator.AnimatorListener {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    override val viewModel: SplashViewModel by lazyThreadSafetyNone {
        ViewModelProviders.of(this, factory)[SplashViewModel::class.java]
    }
    override val layoutResource: Int = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDataBinding()
        setObserver()
        setPlayLottie()
    }

    private fun setPlayLottie() {
        lottieView.playAnimation()
        lottieView.addAnimatorListener(this)
    }

    private fun setObserver() {
        viewModel.result.observe(this, Observer { result ->
            when (result) {
                is SplashViewModel.Result.StartLoginActivity -> startActivity(result.classType)
                is SplashViewModel.Result.StartMainActivity -> startActivity(result.classType)
            }

        })
    }

    private fun <T> startActivity(classType: Class<T>) {
        startActivity(Intent(this, classType))
        finish()
    }

    private fun setDataBinding() {
        viewDataBinding.apply {
            splashViewModel = viewModel
            setLifecycleOwner(this@SplashActivity)
        }
    }

    override fun onAnimationRepeat(animation: Animator?) {
    }

    override fun onAnimationEnd(animation: Animator?) {
        viewModel.checkUser()
    }

    override fun onAnimationCancel(animation: Animator?) {
    }

    override fun onAnimationStart(animation: Animator?) {
    }
}
