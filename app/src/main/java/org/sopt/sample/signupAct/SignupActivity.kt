package org.sopt.sample.signupAct

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import org.sopt.sample.LoginActivity
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivitySignupBinding


class SignupActivity: AppCompatActivity() {
    //private lateinit var binding: ActivitySignupBinding
    // [4주차] private val signupService = ServicePool.srvc_sopt

    // [6주차 실습]
    private val viewModel by viewModels<SignupViewModel>()
    // [6주차 과제]
    private lateinit var binding: ActivitySignupBinding
    private lateinit var inputViewModel: SingupInputViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        binding.lifecycleOwner = this

        inputViewModel = ViewModelProvider(this).get(SingupInputViewModel(application)::class.java)
        binding.vm = inputViewModel

        // [6주차 과제] check if input is valid
        inputViewModel.setup(this, this)
        clickBtn()
    }

    // 회원가입 버튼이 클릭되면
    private fun clickBtn() {
        binding.btnSignup.setOnClickListener {
            viewModel.signup(
                binding.idInput.text.toString(),
                binding.pwInput.text.toString(),
                binding.mbtiInput.text.toString()
            )
        }

        // 서버 통신
        viewModel.signupResult.observe(this) {
            if (it.status == 201) {
                Log.d("회원가입", "live data 자료형 signupResult의 value가 변경됨이 관찰됐고 회원가입이 잘 됐다는군")
                val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                intent.putExtra("id", binding.idInput.text.toString())
                intent.putExtra("pw", binding.pwInput.text.toString())
                intent.putExtra("mbti", binding.mbtiInput.text.toString())
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }
}