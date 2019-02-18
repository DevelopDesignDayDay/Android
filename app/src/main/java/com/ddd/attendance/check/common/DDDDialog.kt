package com.ddd.attendance.check.common

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.ddd.attendance.check.R
import com.ddd.attendance.check.vm.MainViewModel
import com.ddd.attendance.check.vm.MainViewModel.Companion.MSG_ATTENDANCE_SUCCESS
import kotlinx.android.synthetic.main.dialog_ddd.*


class DDDDialog(context: Context, private val data: Pair<UserType, String>) : Dialog(context) {
    private lateinit var dddDialogEventListener: DDDDialogEventListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(com.ddd.attendance.check.R.layout.dialog_ddd)

        setInitView()
        setBackGround()
        setDialogMarkImg(data.second)
        tvMessage.text = data.second
        btnOK.setOnClickListener {
            dddDialogEventListener.onClick(this)
            dismiss()
        }
    }

    private fun setDialogMarkImg(message: String) {
        val background = ContextCompat.getDrawable(
            context,
            if (message == MainViewModel.MSG_ATTENDANCE_START || message == MSG_ATTENDANCE_SUCCESS) R.drawable.check_icon else R.drawable.exclamation
        )
        imgMark.background = background
    }

    private fun setInitView() {
        val metrics = DisplayMetrics()
        window?.windowManager?.defaultDisplay?.getMetrics(metrics)
        val width = (metrics.widthPixels * 0.8).toInt()
        window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun setBackGround() {
        linearDialog.background = ContextCompat.getDrawable(
            context,
            if (data.first == UserType.BASIC) R.drawable.dialog_ddd_background else R.drawable.dialog_ddd_background_admin
        )
    }

    fun setDialogListener(dddDialogEventListener: DDDDialogEventListener): DDDDialog {
        this.dddDialogEventListener = dddDialogEventListener
        return this
    }

    interface DDDDialogEventListener {
        fun onClick(dialog: Dialog)
    }
}