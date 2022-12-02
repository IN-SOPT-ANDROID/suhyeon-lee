package org.sopt.sample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.databinding.ActivityLoginBinding
import org.sopt.sample.signupAct.SignupActivity


class LoginActivity: AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private var id: String = ""
    private var pw: String = ""
    private var mbti: String = ""

    // [7주차] viewModel : live data가 저장돼있는 ViewModel
    private val viewModel by viewModels<LoginViewModel>()

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

    private fun clickLoginBtn() {
        // 1) 로그인 버튼이 클릭되면
        binding.btnLogin.setOnClickListener {
            // [7주차] ViewModel-LiveData로 서버 통신 구현
            viewModel.login(
                binding.idInput.text.toString(),
                binding.pwInput.text.toString()
            )
        }

        // [7주차] 2) LiveData의 observe() 함수를 통해 observe 객체를 등록한다
        // this : 생명주기를 관장하는 객체로,
        // activity에서는 this(activity의 생명주기에 맞춰 데이터 구독)
        // fragment에서는 viewLifecycleOwner를 지정해주면 된다
        // 이 람다 객체가 observer(관찰자)다 (코틀린에서는 함수도 객체이기 때문)
        // observe 메소드가 LiveData에서 데이터를 관찰할 관찰자를 등록하는 메소드
        viewModel.loginResult.observe(this) {
            if (it.status == 200) {
                Toast.makeText(this@LoginActivity, "환영합니다", Toast.LENGTH_SHORT).show()
                Log.d("로그인", "live data 자료형 loginResult의 value가 변경됨이 관찰됐고 로그인 잘 됐다는군")
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                startActivity(intent)
            }
            else {
                Toast.makeText(this@LoginActivity, "로그인 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 회원가입 버튼이 클릭되면 (콜백함수)
    private fun clickSignupBtn() {
        binding.btnSignup.setOnClickListener { // 회원가입
            val intent = Intent(this, SignupActivity::class.java)
            resultLauncher.launch(intent)
        }
    }
}