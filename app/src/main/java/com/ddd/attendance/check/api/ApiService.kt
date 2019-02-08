package com.ddd.attendance.check.api

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
}