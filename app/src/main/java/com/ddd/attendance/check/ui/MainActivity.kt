package com.ddd.attendance.check.ui

import android.app.Dialog
import android.os.Bundle
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
        setUpDialog()
    }

    private fun setUpDialog() {
        btnAttendance.setOnClickListener {
            DDDDialog(this)
                .setDialogListener(object : DDDDialog.DDDDialogEventListener {
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                    }
                }).show()
        }
    }

    private fun setDataBinding() {
        viewDataBinding.apply {
            mainViewModel = viewModel
            setLifecycleOwner(this@MainActivity)
        }
    }
}
