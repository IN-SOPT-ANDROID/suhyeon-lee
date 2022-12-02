package org.sopt.sample.homeFrag

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sopt.sample.retrofit.LoginResDTO
import org.sopt.sample.retrofit.ReqresResponse
import org.sopt.sample.retrofit.ServicePool
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserInfoViewModel : ViewModel() {
    private val _userInfoResult: MutableLiveData<ReqresResponse> = MutableLiveData()
    val userInfoResult: LiveData<ReqresResponse> = _userInfoResult
    private val userInfoService = ServicePool.srvc_reqres

    fun showUsers() {
        userInfoService.getUserInfo().enqueue(object: Callback<ReqresResponse> {
            override fun onResponse(
                call: Call<ReqresResponse>,
                response: Response<ReqresResponse>
            ) {
                if (response.isSuccessful) {
                    _userInfoResult.value = response.body()
                    Log.d("Reqres", "서버통신 성공, response(ReqresResponse)가 live data 내 value에 담겼다")
                }
                else {
                    Log.d("Reqres", "서버통신은 성공했지만 뭔가 이상한가부다")
                }
            }

            override fun onFailure(call: Call<ReqresResponse>, t: Throwable) {
                Log.d("Reqres", "서버통신 실패")
            }

        })
    }
}