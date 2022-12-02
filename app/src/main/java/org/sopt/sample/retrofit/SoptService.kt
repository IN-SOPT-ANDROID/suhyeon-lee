package org.sopt.sample.retrofit

import android.util.Log
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

// HTTP 메소드를 정의한다
// HTTP 메소드를 작성해 레트로핏이 데이터를 가져올 수 있게 한다
// 인터페이스를 작성하면, 레트로핏 라이브러리가 인터페이스에 정의된 API 엔드포인트들을 자동으로 구현해준다
interface SoptService {
    @POST("api/user/signin") // http://3.39.169.52:3000/api/user/signin
    fun login(
        @Body request: LoginReqDTO
    ): Call<LoginResDTO>

    @POST("api/user/signup")
    fun signup(
        @Body request: SignupReqDTO
    ): Call<SignupResDTO>
}