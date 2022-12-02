package org.sopt.sample.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.sopt.sample.databinding.FragmentHomeBinding
import org.sopt.sample.retrofit.ReqresResponse
import org.sopt.sample.retrofit.RetrofitConnection
import org.sopt.sample.retrofit.ServicePool
import org.sopt.sample.retrofit.UserInfoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment: Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding) { "homeFragment _binding 오류" }
    private val userInfoService = ServicePool.srvc_reqres

    override fun onCreateView(
        inflater: LayoutInflater, // 뷰를 생성하는 객체
        container: ViewGroup?, // 생성할 뷰(자식 뷰)가 들어갈 부모 뷰
        savedInstanceState: Bundle? // 이전 프래그먼트 객체에서 전달된 데이터(번들)
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getUserInfo()
    }

    // 레트로핏 클래스를 이용해 필요한 데이터 받아오기
    private fun getUserInfo() {
        // [수현] 레트로핏 객체를 이용해 UserInfoService 인터페이스 구현체를 가져올 수 있다
        // val retrofitAPI = RetrofitConnection.ApiFactory.getInstance().create(UserInfoService::class.java)

        userInfoService.getUserInfo().enqueue(object: Callback<ReqresResponse> {
            override fun onResponse(
                call: Call<ReqresResponse>,
                response: Response<ReqresResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { showUserInfo(it) }
                    Log.d("USERINFO-RESPONSE/SUCCESS", "USERINFO 성공!! $response")
                }
                else {
                    Log.d("USERINFO-RESPONSE/semi-SUCCESS", "반만 성공.. $response")
                }
            }

            override fun onFailure(
                call: Call<ReqresResponse>,
                t: Throwable
            ) {
                Log.d("USERINFO-RESPONSE/FAILURE", "USERINFO 실패 ㅠㅠ, ${t.message}")
            }
        })
    }

    private fun showUserInfo(response: ReqresResponse) {
        val adapter = UserInfoAdapter(response.data)
        binding.rvUserInfo.adapter = adapter
    }
}