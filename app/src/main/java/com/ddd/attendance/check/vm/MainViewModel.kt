package com.ddd.attendance.check.vm

import android.content.SharedPreferences
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ddd.attendance.check.common.UserType
import com.ddd.attendance.check.data.repository.AttendanceRepository
import com.ddd.attendance.check.data.repository.UserRepository
import com.ddd.attendance.check.db.entity.User
import com.ddd.attendance.check.di.module.SharedPreferencesModule
import com.ddd.attendance.check.model.Attendance
import com.ddd.attendance.check.utill.SingleLiveEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val attendanceRepository: AttendanceRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _isAdmin = MutableLiveData<Boolean>()
    private val _isAttendanceNumber = MutableLiveData<Int>()
    private val _isAttendanceStart = MutableLiveData<Boolean>()
    private val _btnEnableLogin = MutableLiveData<Boolean>()
    private val _showToastError = MutableLiveData<String>()
    private val _isProgressbar = MutableLiveData<Boolean>()

    val isAdmin: LiveData<Boolean> = _isAdmin
    val isAttendanceNumber: LiveData<Int> = _isAttendanceNumber
    val isAttendanceStart: LiveData<Boolean> = _isAttendanceStart
    val editNumberAttendance = ObservableField<String>()
    val btnEnableAttendance: LiveData<Boolean> get() = _btnEnableLogin
    val showToastError: LiveData<String> get() = _showToastError
    val progressbar: LiveData<Boolean> = _isProgressbar

    val showDDDDialog = SingleLiveEvent<Pair<UserType, String>>()
    val expireToken = SingleLiveEvent<String>()

    init {
        _isAttendanceStart.value = sharedPreferences.getBoolean(SharedPreferencesModule.KEY_STATUS, false)
        _isAttendanceNumber.value = sharedPreferences.getInt(SharedPreferencesModule.KEY_NUMBER, 0)
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
        attendanceStart?.apply {
            _isAttendanceNumber.postValue(number)
            _isAttendanceStart.postValue(true)
            sharedPreferences.edit().apply {
                putBoolean(SharedPreferencesModule.KEY_STATUS, true)
                putInt(SharedPreferencesModule.KEY_NUMBER, number ?: 0)
                apply()
            }

        }
        showDDDDialog(MSG_ATTENDANCE_START)
    }

    private fun attendanceEndUI() {
        _isAttendanceNumber.postValue(EMPTY_NUMBER)
        _isAttendanceStart.postValue(false)
        sharedPreferences.edit().apply {
            putBoolean(SharedPreferencesModule.KEY_STATUS, false)
            putInt(SharedPreferencesModule.KEY_NUMBER, 0)
            apply()
        }
        showDDDDialog(MSG_ATTENDANCE_END)
    }

    // 출석 버튼 클릭
    fun attendance() {
        _isProgressbar.value = true
        GlobalScope.launch {
            try {
                if (isAdmin.value == true) {
                    if (_isAttendanceStart.value == null || _isAttendanceStart.value == false) attendanceStart()
                    else attendanceEnd()
                } else attendanceCheck()
            } catch (e: IOException) {
                _isProgressbar.postValue(false)
                _showToastError.postValue(MSG_ATTENDANCE_END)
            }
        }
    }
    // 관리자 출첵 시작
    private suspend fun attendanceStart() {
        val response = attendanceRepository.attendanceStart()
        _isProgressbar.postValue(false)
        if (response.isSuccessful) attendanceStartUI(response.body())
        else errorParsingDialog(response.errorBody()?.string())

    }

    // 관리자 출첵 종료
    private suspend fun attendanceEnd() {
        val response = attendanceRepository.attendsEnd()
        _isProgressbar.postValue(false)
        if (response.isSuccessful) {
            attendanceEndUI()
        } else _showToastError.postValue(MSG_ALREADY_START)
    }

    //일반 팀원 출첵
    private suspend fun attendanceCheck() {
        val response = attendanceRepository.attendanceCheck(
            userRepository.getUsers()[0].id.toString(), editNumberAttendance.get() ?: ""
        )
        _isProgressbar.postValue(false)
        if (response.isSuccessful) showDDDDialog(MSG_ATTENDANCE_SUCCESS)
        else errorParsingDialog(response.errorBody()?.string())
    }

    //토큰 리프레시
    private suspend fun requestRefreshToken() {
        GlobalScope.launch {
            try {
                val user = userRepository.getUsers()[0]
                val response = userRepository.refreshToken(user.refreshToken)
                _isProgressbar.postValue(false)
                if (response.isSuccessful) {
                    val body = response.body()
                    userRepository.saveUsers(
                        User(
                            user.id,
                            user.name,
                            user.account,
                            user.type,
                            body?.accessToken ?: "",
                            body?.refreshToken ?: ""
                        )
                    )
                } else {
                    userRepository.deleteUser(user)
                    expireToken.postValue(MSG_LOG_OUT)
                }
            } catch (e: Exception) {
                _isProgressbar.postValue(false)
                e.printStackTrace()
            }
        }
    }

    private suspend fun errorParsingDialog(msg: String?) {
        val jsonObject = JSONObject(msg)
        jsonObject.optString(KEY_MSG_OBJ_JSON, "").let { message ->
            if (message == INVALID_TOKEN) requestRefreshToken()
            else showDDDDialog(message)
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
        const val MSG_LOG_OUT = "사용 시간이 만료되어 로그아웃 되었습니다."
        const val KEY_MSG_OBJ_JSON = "message"
        const val INVALID_TOKEN = "Invalid Token"
        const val EMPTY_NUMBER = 0
    }
}