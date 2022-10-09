package org.sopt.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.sopt.sample.databinding.ActivityHomeBinding

class HomeActivity: AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        binding.idOutput.text = "ID: ".plus(intent.getStringExtra("id"))
        binding.mbtiOutput.text = "MBTI: ".plus(intent.getStringExtra("mbti"))
    }
}