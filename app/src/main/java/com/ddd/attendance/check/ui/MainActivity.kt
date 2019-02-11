package com.ddd.attendance.check.ui

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.ddd.attendance.check.R
import com.ddd.attendance.check.base.DDDActivity
import com.ddd.attendance.check.common.DDDDialog
import com.ddd.attendance.check.databinding.ActivityMainBinding
import com.ddd.attendance.check.utill.lazyThreadSafetyNone
import com.ddd.attendance.check.vm.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : DDDActivity<ActivityMainBinding, MainViewModel>() {

    override val layoutResource: Int = R.layout.activity_main
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    override val viewModel: MainViewModel by lazyThreadSafetyNone { ViewModelProviders.of(this, factory)[MainViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDataBinding()
        setUpLiveData()
        viewModel.checkUserType()

        btnAttendance.setOnClickListener {
            viewModel.showDDDDialog()
        }
    }

    private fun setUpLiveData() {
        viewModel.showDDDDialog.observe(this, Observer { userType ->
            DDDDialog(this, userType)
                .setDialogListener(object : DDDDialog.DDDDialogEventListener {
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                    }
                }).show()
        })

        viewModel.isAdmin.observe(this, Observer { isAdmin ->
            val view = window.decorView
            if (isAdmin) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = ContextCompat.getColor(this, R.color.attendance_admin_background_color)
            } else {
                view.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                window.statusBarColor = Color.WHITE
            }
        })
    }

    private fun setDataBinding() {
        viewDataBinding.apply {
            mainViewModel = viewModel
            setLifecycleOwner(this@MainActivity)
        }
    }
}
