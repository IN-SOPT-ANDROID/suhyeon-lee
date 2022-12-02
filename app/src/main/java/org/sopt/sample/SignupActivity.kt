package org.sopt.sample

import android.R.attr.button
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.databinding.ActivitySignupBinding
import org.sopt.sample.retrofit.ServicePool
import org.sopt.sample.retrofit.SignupReqDTO
import org.sopt.sample.retrofit.SignupResDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignupActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    // [4주차] private val signupService = ServicePool.srvc_sopt
    // [7주차]
    private val viewModel by viewModels<SignupViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        watchInput()
        clickBtn()
    } // onCreate()

    private fun watchInput() {
        var par = object : TextWatcher {
            // 1) et는 uneditable 상태, 입력 전에 호출
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            // 2) et는 uneditable 상태, 입력 중에 호출
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.btnSignup.isEnabled =
                    binding.idInput.length() > 0 && binding.pwInput.length() > 0 && binding.mbtiInput.length() > 0
            }
            // 3) et는 editable 상태, 입력 완료 후 호출
            override fun afterTextChanged(s: Editable?) { }
        }
        binding.idInput.addTextChangedListener(par)
        binding.pwInput.addTextChangedListener(par)
        binding.mbtiInput.addTextChangedListener(par)
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

    /* [4주차] 서버 통신 : callback 구조
    private fun trySignin() {
        signupService.signup(
            SignupReqDTO(
                binding.idInput.text.toString(),
                binding.pwInput.text.toString(),
                binding.mbtiInput.text.toString()
            )
        ).enqueue(object: Callback<SignupResDTO> {
            override fun onResponse(
                call: Call<SignupResDTO>,
                response: Response<SignupResDTO>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(this@SignupActivity, "회원가입 성공!", Toast.LENGTH_SHORT).show()
                    Log.d("SIGNUP/SUCCESS", "회원가입 성공!! $response")
                    val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                    intent.putExtra("id", binding.idInput.text.toString())
                    intent.putExtra("pw", binding.pwInput.text.toString())
                    intent.putExtra("mbti", binding.mbtiInput.text.toString())
                    setResult(RESULT_OK, intent)
                    finish()
                }
                else { // 통신은 성공했으나 회원가입 실패
                    Log.d("SIGNUP/FAIL", "$response, ${binding.idInput.text}, ${binding.pwInput.text}")
                    Toast.makeText(this@SignupActivity, "회원가입 실패.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(
                call: Call<SignupResDTO>,
                t: Throwable
            ) {
                Toast.makeText(this@SignupActivity, "통신 실패.", Toast.LENGTH_SHORT).show()
                Log.d("SINGUP/SERVER-COM", "통신 실패ㅠㅠ {$t.message}")
            }

        })
    }
    */

    /* [1주차]
    private fun check(): Boolean {
        if (binding.idInput.text.length < 6 || binding.idInput.text.length > 10) {
            Snackbar.make(binding.root, "아이디는 6~10자여야 합니다.", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if (binding.pwInput.text.length < 8 || binding.pwInput.text.length > 12) {
            Snackbar.make(binding.root, "비밀번호는 8~12자여야 합니다.", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if (!(binding.mbtiInput.text.toString().equals("ENFJ", true) ||
                    binding.mbtiInput.text.toString().equals("INFJ", true) ||
                    binding.mbtiInput.text.toString().equals("INTJ", true) ||
                    binding.mbtiInput.text.toString().equals("ENTJ", true) ||
                    binding.mbtiInput.text.toString().equals("ENFP", true) ||
                    binding.mbtiInput.text.toString().equals("INFP", true) ||
                    binding.mbtiInput.text.toString().equals("INTP", true) ||
                    binding.mbtiInput.text.toString().equals("ENTP", true) ||
                    binding.mbtiInput.text.toString().equals("ESFP", true) ||
                    binding.mbtiInput.text.toString().equals("ISFP", true) ||
                    binding.mbtiInput.text.toString().equals("ISTP", true) ||
                    binding.mbtiInput.text.toString().equals("ESTP", true) ||
                    binding.mbtiInput.text.toString().equals("ESFJ", true) ||
                    binding.mbtiInput.text.toString().equals("ISFJ", true) ||
                    binding.mbtiInput.text.toString().equals("ISTJ", true) ||
                    binding.mbtiInput.text.toString().equals("ESTJ", true))
        ) {
            Snackbar.make(binding.root, "MBTI를 제대로 입력해주세요.", Snackbar.LENGTH_SHORT).show()
            return false
        }
        else return true
    }
    */
}