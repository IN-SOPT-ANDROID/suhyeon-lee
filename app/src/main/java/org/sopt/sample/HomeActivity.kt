package org.sopt.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.sopt.sample.databinding.ActivityHomeBinding
import org.sopt.sample.gallery.GalleryFragment
import org.sopt.sample.home.HomeFragment
import org.sopt.sample.search.SearchFragment
// import org.sopt.sample.databinding.ActivitySignupBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root) // R.layout.activity_home
        settingNav()
    }

    // 네비게이션에서 어떤 버튼이 선택되는지에 따라 fragment가 바뀐다
    fun settingNav() {
        binding.nav.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nav_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.home_container, HomeFragment())
                        .commit()
                }
                R.id.nav_gallery -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.home_container, GalleryFragment())
                        .commit()
                }
                R.id.nav_search -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.home_container, SearchFragment())
                        .commit()
                }
            }
            true
        }
    }
}