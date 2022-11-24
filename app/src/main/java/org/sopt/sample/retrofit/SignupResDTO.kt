package org.sopt.sample.retrofit

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignupResDTO(
    @SerialName("status")
    val status: Int,
    @SerialName("message")
    val message: String,
    @SerialName("newUser")
    val newUser: LoginResDTO.User
) {
    @Serializable
    data class User(
        @SerialName("id")
        val id: Int,
        @SerialName("name")
        val name: String,
        @SerialName("profileImage")
        val profileImage: String?,
        @SerialName("bio")
        val bio: String?,
        @SerialName("email")
        val email: String,
        @SerialName("password")
        val password: String,
    )
}