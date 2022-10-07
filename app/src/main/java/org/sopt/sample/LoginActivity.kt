package org.sopt.sample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.databinding.ActivityLoginBinding

class LoginActivity: AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private var id: String = "a"
    private var pw: String = "a"
    private var mbti: String = ""

    private fun setResultSignUp() {
        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == Activity.RESULT_OK) {
                id = result.data?.getStringExtra("id")?:""
                pw = result.data?.getStringExtra("pw")?:""
                mbti = result.data?.getStringExtra("mbti")?:""
                Snackbar.make(binding.root, "회원가입이 완료되었습니다!", Snackbar.LENGTH_SHORT).show()
                binding.idInput.setText(id)
                binding.pwInput.setText(pw)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener { // 로그인
            if (check()) {
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                intent.putExtra("id", id)
                intent.putExtra("mbti", mbti)

                startActivity(intent)
            }
        }

        setResultSignUp()
        binding.btnSignup.setOnClickListener { // 회원가입
            val intent = Intent(this, SignupActivity::class.java)
            resultLauncher.launch(intent)
        }

    }

    fun check(): Boolean {
        if (id == binding.idInput.text.toString() &&
            pw == binding.pwInput.text.toString()) {
            Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show()
            return true
        }
        Snackbar.make(binding.root, "가입 정보가 없습니다. 회원가입을 해주세요.", Snackbar.LENGTH_SHORT).show()
        return false
    }
}
