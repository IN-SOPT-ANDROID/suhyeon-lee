package org.sopt.sample.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.sopt.sample.retrofit.ApiFactory.retrofit_reqres
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// [수현이가 책 보고 찾아서 해본 부분]
class RetrofitConnection {
    // 객체를 하나만 생성하는 싱글턴 패턴을 적용한다
    companion object {
        // API 서버의 주소가 BASE_URL이 된다
        private const val BASE_URL = "https://reqres.in/"
        private var INSTANCE: Retrofit? = null

        // 레트로핏 객체를 생성한다
        fun getInstance(): Retrofit {
            if (INSTANCE == null) { // null인 경우에만 생성
                INSTANCE = Retrofit.Builder()
                    .baseUrl(BASE_URL) // API 베이스 URL 설정
                    .addConverterFactory(GsonConverterFactory.create())
                    // 레트로핏에 컨버터 팩토리를 추가해준다
                    // 컨버터 팩토리: JSON 응답 객체를, 우리가 만든 데이터 클래스 객체로 변환해줌
                    // Gson이라는 레트로핏 기본 컨버터 팩토리를 사용하는 것
                    .build()
            }
            return INSTANCE!!
        }
    }
}