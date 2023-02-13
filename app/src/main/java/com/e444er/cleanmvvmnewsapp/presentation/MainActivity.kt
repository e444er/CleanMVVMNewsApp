package com.e444er.cleanmvvmnewsapp.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.e444er.cleanmvvmnewsapp.R
import com.e444er.cleanmvvmnewsapp.databinding.ActivityMainBinding
import com.e444er.cleanmvvmnewsapp.domain.use_case.GetLanguageCodeUseCase
import com.e444er.cleanmvvmnewsapp.domain.use_case.GetUIModeUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var getLanguageCodeUseCase: GetLanguageCodeUseCase

    @Inject
    lateinit var getUIModeUseCase: GetUIModeUseCase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    val language = getLanguageCodeUseCase().first()

                    AppCompatDelegate.setApplicationLocales(
                        LocaleListCompat.forLanguageTags(
                            language
                        )
                    )
                }
                launch {
                    getUIModeUseCase().collectLatest { uiMode ->
                        if (uiMode == AppCompatDelegate.MODE_NIGHT_YES) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        } else {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        }
                    }
                }
            }
        }


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener() { _, destination, _ ->
            if (destination.id == R.id.articleFragment) {
                binding.bottomNav.visibility = View.GONE
            } else {
                binding.bottomNav.visibility = View.VISIBLE
            }
        }
    }
}