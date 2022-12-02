package org.sopt.sample

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import org.sopt.sample.retrofit.LoginReqDTO
import org.sopt.sample.retrofit.LoginResDTO
import org.sopt.sample.retrofit.ServicePool
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// ViewModel
// lifecycle과 상관 없이(activity의 경우 onCreate(), onStart() 등) 메모리에 계속 존재한다
// 뷰모델은 데이터의 변경 사항을 알려주는 라이브 데이터를 가진다
class LoginViewModel : ViewModel() {

    // LiveData : observer 패턴, 데이터의 변경 사항을 알 수 있음
    // MutableLiveData(값 수정 가능) or 그냥 LiveData(값 수정 불가)

    // [backing property - 변경 전]
    // private val _loginResult: MutableLiveData<LoginResDTO> = MutableLiveData()
    // [backing property - 변경 후]
    /*
    * Kotlin은 불변성을 지향한다.
    * ViewModel '내부'에서는 서버 통신 등의 발행 객체의 내부 상태를 변경해야 하는 일이 있지만
    * '외부'(activity, fragment)에서는 이를 변경할 이유도 없고, 오히려 변경하면 side effect가 생길 수도.
    * 그래서 이런 발행 객체를 사용하는 경우,
    * 상태를 변경할 수 있는 객체는 private으로,
    * 상태를 공개하는 객체는 불변 객체로 공개해야 한다.
    * 이런 기법을 backing property라고 한다.
    * Kotlin Convention을 찾아보면:
    * 뷰모델 내부에서 사용하는 변수(private)엔 이름에 '_'를 붙인다
    * 그 변수를 외부적으로 사용할 땐(public) 이름에 '_'가 없는 변수를 선언해 이용한다
    */
    // 뷰모델 내부에서 사용하는 변수는 값을 변경 가능하도록(mutable로) 설정
    // 뷰모델 내부에서는 _loginResult를, 외부에서는 loginResult를 사용하면 된다
    private val _loginResult: MutableLiveData<LoginResDTO> = MutableLiveData()
    val loginResult: LiveData<LoginResDTO> = _loginResult

    // 위의 한 줄은 아래 두 줄과 동일하다.
    // public하게 사용할 loginResult는 외부에서 값을 변경하면 안 되므로 mutable하지 않은 LiveData로 설정하고
    // (외부에서) getter 함수를 이용해 뷰모델 내의 loginResult에 접근하는 방식.
    /*
    val loginResult: LiveData<LoginResDTO>
        get() = _loginResult
    */

    private val loginService = ServicePool.srvc_sopt

    // 이 부분은: '서버에서 값을 받아오는 로직 + 값을 일부 가공하는 로직'(a)
    // LoginActivity의 [4주차]tryLogin()에서는 a와 '가공된 데이터를 받아와 뷰를 그리는 로직'(b)이 합체돼있었는데
    // a는 LoginActivity에서, b는 ViewModel에서 구현해서 분리해주었다
    fun login(email: String, pw: String) {
        loginService.login(
            LoginReqDTO(email, pw)
        ).enqueue(object: Callback<LoginResDTO> {
            override fun onResponse(
                call: Call<LoginResDTO>,
                response: Response<LoginResDTO>
            ) {
                if (response.isSuccessful) {
                    // 서버 통신의 결과값을 live data 내의 value에 넣어 통신 성공 시의 데이터를 구독받을 수 있게 해줌
                    // 이제 이 value를 LoginActivity에서 사용할 수 있게 해주자
                    _loginResult.value = response.body()
                    Log.d("로그인", "서버통신 성공, response(LoginResDTO)가 live data 내 value에 담겼다")
                }
                else {
                    Log.d("로그인", "서버통신은 성공했지만 일치하는 로그인 정보가 없대요")
                }
            }

            override fun onFailure(call: Call<LoginResDTO>, t: Throwable) {
                Log.d("로그인", "서버통신 실패")
            }
        })
    }
}