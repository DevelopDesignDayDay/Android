package com.ddd.attendance.check.common

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.ViewGroup
import kotlinx.android.synthetic.main.dialog_ddd.*


class DDDDialog(context: Context, userType: UserType) : Dialog(context) {
    private lateinit var dddDialogEventListener: DDDDialogEventListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(com.ddd.attendance.check.R.layout.dialog_ddd)
        val metrics = DisplayMetrics()
        window?.windowManager?.defaultDisplay?.getMetrics(metrics)
        val width = (metrics.widthPixels * 0.8).toInt()
        window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)

        btnOK.setOnClickListener {
            dddDialogEventListener.onClick(this)
            dismiss()
        }
    }

    fun setDialogListener(dddDialogEventListener: DDDDialogEventListener): DDDDialog {
        this.dddDialogEventListener = dddDialogEventListener
        return this
    }

    interface DDDDialogEventListener {
        fun onClick(dialog: Dialog)
    }
}