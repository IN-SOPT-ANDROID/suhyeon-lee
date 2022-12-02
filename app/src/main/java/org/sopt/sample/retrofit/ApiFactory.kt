package org.sopt.sample.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.sopt.sample.retrofit.ApiFactory.retrofit_reqres
import org.sopt.sample.retrofit.ApiFactory.retrofit_sopt
import retrofit2.Retrofit

// [솝트 4차 세미나에서 한 부분]
// 레트로핏 빌더를 이용해 레트로핏 클라이언트 객체를 생성한다
object ApiFactory {
    const val URL_REQRES: String = "https://reqres.in/"
    const val URL_SOPT: String = "http://3.39.169.52:3000/"

    // [7주차] logging interceptor
    private val client by lazy {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            ).build()
    }

    // [7주차] appl -> OkHttp Core로 전송된 요청값을 custom(해서 network로 보낼 계획)
    // 근데 SOPT 서버에서 토큰을 발행해주지도 않고, 실제로 해볼 수가 없으니
    // 나중에 혹시 참고할 일이 있을까 하여 남겨만 놓겠다.
    /*
    private val client by lazy {
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            ).build()
    }
    */

    val retrofit_reqres: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(URL_REQRES)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    val retrofit_sopt: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(URL_SOPT)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(client)
            .build()
    }

    inline fun <reified T> create(retrofit: Retrofit): T = retrofit.create<T>(T::class.java)
}

object ServicePool {
    val srvc_reqres = ApiFactory.create<UserInfoService>(retrofit_reqres)
    val srvc_sopt = ApiFactory.create<SoptService>(retrofit_sopt)
}