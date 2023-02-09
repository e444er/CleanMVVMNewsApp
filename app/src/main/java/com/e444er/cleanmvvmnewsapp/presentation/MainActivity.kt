package com.e444er.cleanmvvmnewsapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.e444er.cleanmvvmnewsapp.R
import com.e444er.cleanmvvmnewsapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNav.setupWithNavController(navController)

//        navController.addOnDestinationChangedListener(){ _ ,destination, _ ->
//            if (destination.id == R.id.articleFragment){
//                binding.bottomNav.visibility = View.GONE
//            } else {
//                binding.bottomNav.visibility = View.VISIBLE
//            }
//        }
    }
}