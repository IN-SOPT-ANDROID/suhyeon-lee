package org.sopt.sample.retrofit

import retrofit2.Call
import retrofit2.http.GET

// HTTP 메소드를 작성해 레트로핏이 데이터를 가져올 수 있게 한다
// 인터페이스를 작성하면, 레트로핏 라이브러리가 인터페이스에 정의된 API 엔드포인트들을 자동으로 구현해준다
interface UserInfoService {
    @GET("api/users?page=2") // https://reqres.in/api/users?page=2
    fun getUserInfo(): Call<ReqresResponse>
}