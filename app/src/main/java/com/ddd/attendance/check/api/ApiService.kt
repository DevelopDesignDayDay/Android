package com.ddd.attendance.check.api

import com.ddd.attendance.check.model.Attendance
import com.ddd.attendance.check.model.AttendanceCheckResponse
import com.ddd.attendance.check.model.BaseResponse
import com.ddd.attendance.check.model.LoginResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("/auth/login")
    fun login(@Field("account") id: String, @Field("password") password: String): Deferred<Response<LoginResponse>>

    @FormUrlEncoded
    @POST("/auth/token")
    fun refreshToken(@Field("refreshToken") refreshToken: String): Deferred<Response<BaseResponse>>

    @POST("/attends/start")
    fun attendsStart(): Deferred<Response<Attendance>>

    @POST("/attends/end")
    fun attendsEnd(): Deferred<Response<Attendance>>

    @FormUrlEncoded
    @POST("/attends/check")
    fun attendsCheck(@Field("userId") userId: String, @Field("number") number: String): Deferred<Response<AttendanceCheckResponse>>

}