package org.sopt.sample.retrofit

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginReqDTO(
    // JSON 객체로 변환될 때 'email': 'nu@gmail.com' 같은 식으로 들어갈 수 있다는 표기
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String
)
