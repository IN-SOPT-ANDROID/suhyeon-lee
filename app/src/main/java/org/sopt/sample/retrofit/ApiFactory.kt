package org.sopt.sample.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.sopt.sample.retrofit.ApiFactory.retrofit_reqres
import org.sopt.sample.retrofit.ApiFactory.retrofit_sopt
import retrofit2.Retrofit

// [솝트 4차 세미나에서 한 부분]
object ApiFactory {
    const val URL_REQRES: String = "https://reqres.in/"
    const val URL_SOPT: String = "http://3.39.169.52:3000/"

    val retrofit_reqres by lazy {
        Retrofit.Builder()
            .baseUrl(URL_REQRES)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    val retrofit_sopt by lazy {
        Retrofit.Builder()
            .baseUrl(URL_SOPT)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    inline fun <reified T> create(retrofit: Retrofit): T = retrofit.create<T>(T::class.java)
}

object ServicePool {
    val srvc_reqres = ApiFactory.create<UserInfoService>(retrofit_reqres)
    //val srvc_sopt = ApiFactory.create<UserInfoService>(retrofit_sopt)
}