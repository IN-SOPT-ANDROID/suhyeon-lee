package org.sopt.sample.retrofit

import okhttp3.Interceptor
import okhttp3.Response

// 토큰을 인증해주는 서버가 없기에 참고용으로 남겨만 놓겠다
// 실제로 사용하지는 않는 클래스,,

// network intercpetor : 실제 network - OkHttp Core 모듈 사이에서
// 요청을 하기 직전 & 응답을 내려받은 직후 의 값들을 가져와서 커스텀하고자 할 때 사용

// 서버통신 요청/응답값을 Logcat으로 볼 수 있는 logging-interceptor을 써봤으니
// 이제는 요청/응답값을 직접 커스텀해보자!
// 사용자가 보낸 응답값에, 자동으로 토큰을 넣어줘서, 토큰이 포함된 요청이 network로 가게 하는 로직
class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // chain: OkHttp Core 모듈에서 넘어온 chain 객체를 통해
        // OkHttp core -> network에 요청하기 전 ~
        // network -> OkHttp core에 응답한 후의 값,을 받아올 수 있다

        // chain.request()는 사용자가 보낸 요청값을 가로채는 것
        val orgReq = chain.request()
        // 서버에서 발급받은 토큰을 응답값에 저장하는 로직이 필요하다
        // 그래야 SharedPreference에서 토큰 값을 가져와서 넣어주지
        // newBuilder : 기존의 request를 기반으로, 새로운 parameter를 추가해 새로운 request를 만들어낸다
        val headerReq = orgReq.newBuilder()
            .header("token", "hi" /* 토큰 값을 넣어주기! */ )
            .build()
        // proceed를 통해서 network로부터 응답값을 받아올 수 있고
        // 우리는 응답값에 어떠한 커스텀도 하지 않을 것이므로
        // 일단 고대로 appl로 내려보낸다 (OkHttp core -> appl)
        return chain.proceed(headerReq)
    }
}