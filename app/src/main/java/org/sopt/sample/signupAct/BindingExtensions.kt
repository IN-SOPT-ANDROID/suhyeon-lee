package org.sopt.sample.signupAct

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

object BindingExtensions {
    @JvmStatic
    @BindingAdapter("errorText")
    fun TextInputLayout.setErrorMsg(errorMsg: String?) {
        if (errorMsg.isNullOrEmpty())
            this.isErrorEnabled = false
        else {
            this.isErrorEnabled = true
            this.error = errorMsg
        }
    }
}