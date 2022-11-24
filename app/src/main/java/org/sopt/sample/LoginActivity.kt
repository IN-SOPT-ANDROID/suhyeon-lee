package org.sopt.sample

import android.R.attr.button
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.databinding.ActivityLoginBinding
import org.sopt.sample.home.UserInfoAdapter
import org.sopt.sample.retrofit.LoginReqDTO
import org.sopt.sample.retrofit.LoginResDTO
import org.sopt.sample.retrofit.ReqresResponse
import org.sopt.sample.retrofit.ServicePool
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity: AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private var id: String = ""
    private var pw: String = ""
    private var mbti: String = ""
    private val loginService = ServicePool.srvc_sopt

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setResultSignUp()
        clickLoginBtn()
        clickSignupBtn()
    } // onCreate()

    private fun setResultSignUp() {
        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
            ) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    // SingupActivity에서 가져온 데이터를 이 파일(LoginActivity.kt)의 변수들에 저장
                    id = result.data?.getStringExtra("id")?:""
                    pw = result.data?.getStringExtra("pw")?:""
                    mbti = result.data?.getStringExtra("mbti")?:""
                    Snackbar.make(binding.root, "회원가입이 완료되었습니다!", Snackbar.LENGTH_SHORT).show()
                    // SignupActivity에서 입력받은 id, pw 정보가 이 LoginActivity에서 그대로 보이게
                    binding.idInput.setText(id)
                    binding.pwInput.setText(pw)
            }
        }
    }

    // 로그인 버튼이 클릭되면 (콜백함수)
    private fun clickLoginBtn() {
        binding.btnLogin.setOnClickListener {
            tryLogin()
        }
    }

    // 입력 받은 로그인 정보를 확인 - 서버 통신에서 바뀌어야지?
    private fun checkInput(): Boolean {
        if (id == binding.idInput.text.toString() && pw == binding.pwInput.text.toString()) {
            Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show()
            return true
        }
        else {
            Snackbar.make(binding.root, "가입 정보가 없습니다. 회원가입을 해주세요.", Snackbar.LENGTH_SHORT).show()
            return false
        }
    }

    // 회원가입 버튼이 클릭되면 (콜백함수)
    private fun clickSignupBtn() {
        binding.btnSignup.setOnClickListener { // 회원가입
            val intent = Intent(this, SignupActivity::class.java)
            resultLauncher.launch(intent)
        }
    }

    private fun tryLogin() {
        loginService.login(
            LoginReqDTO(binding.idInput.text.toString(), binding.pwInput.text.toString())
        ).enqueue(object: Callback<LoginResDTO> {
            override fun onResponse(
                call: Call<LoginResDTO>,
                response: Response<LoginResDTO>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(this@LoginActivity, "로그인 성공!", Toast.LENGTH_SHORT).show()
                    Log.d("LOGIN/SUCCESS", "LOGIN 성공!! $response")
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    //intent.putExtra("id", binding.idInput.toString())
                    //intent.putExtra("mbti", binding.pwInput.toString())
                    startActivity(intent)
                }
                else {
                    Toast.makeText(this@LoginActivity, "로그인이 실패했습니다.", Toast.LENGTH_SHORT).show()
                    Log.d(
                        "LOGIN/FAIL",
                        "$response, ${binding.idInput.text}, ${binding.pwInput.text}"
                    )
                }
            }

            override fun onFailure(
                call: Call<LoginResDTO>,
                t: Throwable
            ) {
                Snackbar.make(binding.root, "서버통신 실패", Snackbar.LENGTH_SHORT).show()
                Log.d("LOGIN/SERVER-COM", "LOGIN 실패ㅠㅠ {$t.message}")
            }
        })
    }
}