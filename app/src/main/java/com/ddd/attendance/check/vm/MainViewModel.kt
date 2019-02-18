package com.ddd.attendance.check.vm

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ddd.attendance.check.DDDApplication
import com.ddd.attendance.check.common.NetworkHelper
import com.ddd.attendance.check.common.NetworkHelper.ERROR_MSG
import com.ddd.attendance.check.common.UserType
import com.ddd.attendance.check.data.repository.AttendanceRepository
import com.ddd.attendance.check.data.repository.UserRepository
import com.ddd.attendance.check.model.Attendance
import com.ddd.attendance.check.utill.SharedPreferences
import com.ddd.attendance.check.utill.SingleLiveEvent
import kotlinx.coroutines.*
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject

class MainViewModel @Inject constructor(
        private val userRepository: UserRepository,
        private val attendanceRepository: AttendanceRepository,
        private val dddApplication: DDDApplication
) : ViewModel() {

    private val _isAdmin = MutableLiveData<Boolean>()
    private val _isAttendanceNumberCount = MutableLiveData<Int>()
    private val _isAttendanceNumber = MutableLiveData<Int>()
    private val _isAttendanceStart = MutableLiveData<Boolean>()
    private val _btnEnableLogin = MutableLiveData<Boolean>()
    private val _showToastError = MutableLiveData<String>()

    val isAdmin: LiveData<Boolean> = _isAdmin
    val isAttendanceNumberCount: LiveData<Int> = _isAttendanceNumberCount
    val isAttendanceNumber: LiveData<Int> = _isAttendanceNumber
    val isAttendanceStart: LiveData<Boolean> = _isAttendanceStart
    val editNumberAttendance = ObservableField<String>()
    val btnEnableAttendance: LiveData<Boolean> get() = _btnEnableLogin
    val showToastError: LiveData<String> get() = _showToastError

    val showDDDDialog = SingleLiveEvent<Pair<UserType, String>>()


    init {
        _isAttendanceStart.value = SharedPreferences.getStatus(dddApplication)
    }

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
        _isAttendanceNumberCount.postValue(EMPTY_NUMBER)
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
    // 관리자 출첵 시작
    private suspend fun attendanceStart() {
        val response = attendanceRepository.attendanceStart()
        if (response.isSuccessful) {
            SharedPreferences.saveStatus(dddApplication, true)
            attendanceStartUI(response.body())
            startTimer()
        } else {
            _showToastError.postValue(MSG_ALREADY_START)
        }
    }

    private var job: Job? = null
    private suspend fun startTimer() {
        CoroutineScope(Dispatchers.Default).launch {
            job = launch(Dispatchers.Main) {
                60.countDown()
            }
            job?.join()
        }
    }
    // 관리자 출첵 종료
    private suspend fun attendanceEnd() {
        val response = attendanceRepository.attendsEnd()
        if (response.isSuccessful) {
            SharedPreferences.saveStatus(dddApplication, false)
            attendanceEndUI()
            if (job != null) job!!.cancel()
        } else _showToastError.postValue(MSG_ALREADY_START)
    }

    //일반 팀원 출첵
    private suspend fun attendanceCheck() {
        GlobalScope.launch {
            try {
                val response = attendanceRepository.attendanceCheck(
                    userRepository.getUsers()[0].id.toString(),
                    editNumberAttendance.get() ?: ""
                )
                if (response.isSuccessful) {
                    showDDDDialog(MSG_ATTENDANCE_SUCCESS)
                } else {
                    JSONObject(response.errorBody()?.string()).run {
                        showDDDDialog(optString(KEY_MSG_OBJ_JSON, ""))
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun Int.countDown() {
        for (index in this downTo 0) {
            _isAttendanceNumberCount.postValue(index)
            delay(COUNT_DELAY)
        }
    }

    private fun showDDDDialog(message: String) {
        showDDDDialog.postValue(Pair(if (_isAdmin.value == true) UserType.ADMIN else UserType.BASIC, message))
    }

    companion object {
        const val MSG_ATTENDANCE_SUCCESS = "출석체크가 완료되었습니다."
        const val MSG_ATTENDANCE_START = "출석체크가 시작되었습니다."
        const val MSG_ATTENDANCE_END = "출석체크가 종료되었습니다."
        const val MSG_ALREADY_START = "이미 출석 체크가 시작되었습니다.\n 잠시후 다시 시도 해주세요."
        const val KEY_MSG_OBJ_JSON = "message"
        const val EMPTY_NUMBER = 0
        const val COUNT_DELAY: Long = 1000
    }
}