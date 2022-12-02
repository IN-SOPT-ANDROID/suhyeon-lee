package org.sopt.sample.signupAct

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sopt.sample.retrofit.ServicePool
import org.sopt.sample.retrofit.SignupReqDTO
import org.sopt.sample.retrofit.SignupResDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// 서버 통신을 위한 ViewModel
class SignupViewModel : ViewModel(){
    private val _signupResult: MutableLiveData<SignupResDTO> = MutableLiveData()
    val signupResult: LiveData<SignupResDTO> = _signupResult
    private val signupService = ServicePool.srvc_sopt

    fun signup(id: String, pw: String, mbti: String) {
        signupService.signup(
            SignupReqDTO(id, pw, mbti)
        ).enqueue(object: Callback<SignupResDTO> {
            override fun onResponse(
                call: Call<SignupResDTO>,
                response: Response<SignupResDTO>
            ) {
                if (response.isSuccessful) {
                    _signupResult.value = response.body()
                    Log.d("회원가입", "서버통신 성공, response(SignupResDTO)가 live data 내 value에 담겼다")
                }
                else {
                    Log.d("회원가입", "서버 통신은 되지만 입력된 정보가 이상하여(서버 내 중복 정보 등) 회원가입을 할 수 없습니다.")
                }
            }

            override fun onFailure(call: Call<SignupResDTO>, t: Throwable) {
                Log.d("회원가입", "서버통신 실패")
            }

        })
    }
}