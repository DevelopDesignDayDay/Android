package com.ddd.attendance.check.ui

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.ddd.attendance.check.R
import com.ddd.attendance.check.base.DDDActivity
import com.ddd.attendance.check.common.DDDDialog
import com.ddd.attendance.check.common.UserType
import com.ddd.attendance.check.databinding.ActivityMainBinding
import com.ddd.attendance.check.extension.observe
import com.ddd.attendance.check.extension.showShortToast
import com.ddd.attendance.check.utill.lazyThreadSafetyNone
import com.ddd.attendance.check.vm.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : DDDActivity<ActivityMainBinding, MainViewModel>() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    override val viewModel: MainViewModel by lazyThreadSafetyNone {
        ViewModelProviders.of(this, factory)[MainViewModel::class.java]
    }

    override val layoutResource: Int = R.layout.activity_main
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDataBinding()
        setObserver()
        viewModel.checkUserType()
    }

    private fun setObserver() {
        observe(viewModel.showDDDDialog, ::showDialog)
        observe(viewModel.showToastError, ::showToastError)
        observe(viewModel.isAttendanceStart, ::btnAdminBackground)
        observe(viewModel.isAdmin, ::statusBarColor)
        observe(viewModel.expireToken, ::expireToken)
    }

    private fun showToastError(msg: String) {
        showShortToast(msg)
    }

    private fun showDialog(data: Pair<UserType, String>) {
        DDDDialog(this, data)
                .setDialogListener(object : DDDDialog.DDDDialogEventListener {
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                    }
                }).show()
    }

    private fun btnAdminBackground(isAdmin: Boolean) {
        val background = if (isAdmin) R.drawable.login_btn_possible_admin else R.drawable.login_btn_impossible
        btnAdminAttendance.background = ContextCompat.getDrawable(this, background)
    }

    private fun statusBarColor(isAdmin: Boolean) {
        val view = window.decorView
        if (isAdmin) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, R.color.attendance_admin_background_color)
        } else {
            view.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = Color.WHITE
        }
    }

    private fun expireToken(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun setDataBinding() {
        viewDataBinding.apply {
            mainViewModel = viewModel
            setLifecycleOwner(this@MainActivity)
        }
    }
}
