package org.sopt.sample.signupAct

import android.app.Application
import android.content.Context
import androidx.lifecycle.*

// 들어오늘 ID/PW input이 한글자 한글자 유효한지 확인하는 ViewModel
class SingupInputViewModel(application: Application): AndroidViewModel(application) {
    var idLiveData: MutableLiveData<String> = MutableLiveData()
    var pwLiveData: MutableLiveData<String> = MutableLiveData()
    var errId: MutableLiveData<String> = MutableLiveData()
    var errPw: MutableLiveData<String> = MutableLiveData()

    private var isIdValid: Boolean = false
    private var isPwValid: Boolean = false
    var isValidLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun setup(lifecycleOwner: LifecycleOwner, context: Context) {
        idLiveData.observe(lifecycleOwner, Observer { id ->
            val validationModel: ValidationModel = id.validateId(context)
            isIdValid = validationModel.isValid
            validateInput(isIdValid, isPwValid)
            errId.postValue(validationModel.msg)
        })
        pwLiveData.observe(lifecycleOwner, Observer { pw ->
            val validationModel: ValidationModel = pw.validatePw(context)
            isPwValid = validationModel.isValid
            validateInput(isIdValid, isPwValid)
            errPw.postValue(validationModel.msg)
        })
    }

    private fun validateInput(id: Boolean, pw: Boolean) {
        isValidLiveData.postValue(id && pw)
    }
}