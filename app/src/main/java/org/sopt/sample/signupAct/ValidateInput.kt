package org.sopt.sample.signupAct

import android.content.Context

class ValidationModel(val isValid: Boolean, val msg: String)

fun String.validateId(context: Context): ValidationModel {
    val patterns: Regex = "[a-zA-Z0-9]".toRegex()
    if (this.trim().isEmpty())
        return ValidationModel(true, "")
    if (!patterns.containsMatchIn(this))
        return ValidationModel(false, "영문 혹은 숫자로만 구성돼야 합니다")
    if (this.length < 6 || this.length > 10 )
        return ValidationModel(false, "6~10자로 구성돼야 합니다")
    else return ValidationModel(true, "")
}

fun String.validatePw(context: Context): ValidationModel {
    val patterns: Regex = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&*])[a-zA-Z0-9!@#\$%^&*]{6,12}$".toRegex()
    if (this.trim().isEmpty())
        return ValidationModel(true, "")
    if (this.length < 6 || this.length > 12)
        return ValidationModel(false, "6~12자로 구성돼야 합니다")
    if (!patterns.containsMatchIn(this))
        return ValidationModel(false, "영문, 숫자, 특수문자를 각각 1개 이상 포함해야 합니다")
    else return ValidationModel(true, "")
}