package com.ddd.attendance.check.vm

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ddd.attendance.check.common.NetworkHelper
import com.ddd.attendance.check.common.NetworkHelper.ERROR_MSG
import com.ddd.attendance.check.common.UserType
import com.ddd.attendance.check.data.repository.AttendanceRepository
import com.ddd.attendance.check.data.repository.UserRepository
import com.ddd.attendance.check.model.Attendance
import com.ddd.attendance.check.utill.SingleLiveEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val attendanceRepository: AttendanceRepository
) : ViewModel() {

    private val _isAdmin = MutableLiveData<Boolean>()
    private val _isAttendanceNumber = MutableLiveData<Int>()
    private val _isAttendanceStart = MutableLiveData<Boolean>()
    private val _btnEnableLogin = MutableLiveData<Boolean>()
    private val _showToastError = MutableLiveData<String>()

    val isAdmin: LiveData<Boolean> = _isAdmin
    val isAttendanceNumber: LiveData<Int> = _isAttendanceNumber
    val isAttendanceStart: LiveData<Boolean> = _isAttendanceStart
    val editNumberAttendance = ObservableField<String>()
    val btnEnableAttendance: LiveData<Boolean> get() = _btnEnableLogin
    val showToastError: LiveData<String> get() = _showToastError

    val showDDDDialog = SingleLiveEvent<Pair<UserType, String>>()

    fun onInputNumberTextChanged(input: CharSequence) {
        editNumberAttendance.set(input.toString())
        _btnEnableLogin.value = !editNumberAttendance.get().isNullOrEmpty()
    }

    fun checkUserType() {
        GlobalScope.launch {
            userRepository.getUsers()
                .map { it.type }
                .map { userType ->
                    if (userType == UserType.BASIC.ordinal) basicUserUI() else adminUserUI()
                }
        }
    }

    private fun basicUserUI() {
        _isAdmin.postValue(false)
        _btnEnableLogin.postValue(false)
    }

    private fun adminUserUI() {
        _isAdmin.postValue(true)
        _btnEnableLogin.postValue(true)
    }

    private fun attendanceStartUI(attendanceStart: Attendance?) {
        attendanceStart?.let { result ->
            if (result.status == NetworkHelper.SUCCESS) {
                _isAttendanceNumber.postValue(result.number)
                _isAttendanceStart.postValue(true)
            }
        }
        showDDDDialog(MSG_ATTENDANCE_START)
    }

    private fun attendanceEndUI() {
        _isAttendanceNumber.postValue(EMPTY_NUMBER)
        _isAttendanceStart.postValue(false)
        showDDDDialog(MSG_ATTENDANCE_END)
    }

    fun attendance() {
        GlobalScope.launch {
            try {
                if (isAdmin.value == true) {
                    if (_isAttendanceStart.value == null || _isAttendanceStart.value == false) attendanceStart()
                    else attendanceEnd()
                } else attendanceCheck()
            } catch (e: IOException) {
                _showToastError.postValue(ERROR_MSG)
            }
        }

    }

    private suspend fun attendanceStart() {
        val response = attendanceRepository.attendanceStart()
        if (response.isSuccessful) attendanceStartUI(response.body())
        else _showToastError.postValue(response.body()?.message)
    }

    private suspend fun attendanceEnd() {
        val response = attendanceRepository.attendsEnd()
        if (response.isSuccessful) attendanceEndUI()
        else _showToastError.postValue(response.body()?.message)
    }

    private fun attendanceCheck() {

    }

    private fun showDDDDialog(message: String) {
        showDDDDialog.postValue(Pair(if (_isAdmin.value == true) UserType.ADMIN else UserType.BASIC, message))
    }

    companion object {
        const val MSG_ATTENDANCE_START = "출석체크가 시작되었습니다."
        const val MSG_ATTENDANCE_END = "출석체크가 종료되었습니다."
        const val EMPTY_NUMBER = 0
    }
}