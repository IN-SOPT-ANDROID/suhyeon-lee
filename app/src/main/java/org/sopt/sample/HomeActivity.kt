package org.sopt.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.sopt.sample.databinding.ActivityHomeBinding
import org.sopt.sample.databinding.ActivitySignupBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
                .add(R.id.home_container, HomeFragment())
                .commit()

        binding.bnv.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.bnv_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.home_container, HomeFragment())
                        .commit()
                }
                R.id.bnv_gallery -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.home_container, GalleryFragment())
                        .commit()
                }
                R.id.bnv_search -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.home_container, SearchFragment())
                        .commit()
                }
            }
            true
        }
    }
}