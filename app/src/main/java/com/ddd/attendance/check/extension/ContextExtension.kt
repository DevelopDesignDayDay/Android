package com.ddd.attendance.check.extension

import android.content.Context
import android.widget.Toast

fun Context.showShortToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}