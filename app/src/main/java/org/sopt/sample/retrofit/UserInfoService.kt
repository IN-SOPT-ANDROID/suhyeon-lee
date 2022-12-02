package org.sopt.sample.retrofit

import retrofit2.Call
import retrofit2.http.GET

interface UserInfoService {
    @GET("api/users?page=2") // https://reqres.in/api/users?page=2
    fun getUserInfo(): Call<ReqresResponse>
}