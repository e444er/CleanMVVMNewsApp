package com.e444er.cleanmvvmnewsapp.presentation.settings

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.e444er.cleanmvvmnewsapp.R
import com.e444er.cleanmvvmnewsapp.databinding.FragmentSettingsBinding
import com.e444er.cleanmvvmnewsapp.domain.model.supportedLanguages
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSettingsBinding.bind(view)
        _binding = binding

        collectDataLifecycle()
        setListenerSwitch()

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            supportedLanguages.map { requireContext().getString(it.textId) }
        )

        binding.spinner.adapter = adapter


        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                p1: View?,
                position: Int,
                p3: Long
            ) {
                val isoCode = supportedLanguages[position].iso
                viewModel.updateLanguageIsoCode(isoCode)
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(isoCode))
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }

    private fun setListenerSwitch() {
        binding.switchDarkTheme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Update uiMode to DarkTheme
                updateUIMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                // Update uiMode to LightTheme
                updateUIMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun updateUIMode(uiMode: Int) {
        viewModel.updateUIMode(uiMode)
        AppCompatDelegate.setDefaultNightMode(uiMode)
    }

    private fun collectDataLifecycle() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.getLanguageIsoCode().collectLatest { lanCode ->
                        val selectedLangIndex = supportedLanguages.indexOfFirst {
                            it.iso == lanCode
                        }
                        binding.spinner.setSelection(selectedLangIndex)
                    }
                }
                launch {
                    viewModel.getUIMode().collectLatest { uiMode ->
                        binding.switchDarkTheme.isChecked =
                            uiMode == AppCompatDelegate.MODE_NIGHT_YES
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}