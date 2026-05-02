package com.example.gamelistxml.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.gamelistxml.MainViewModel
import com.example.gamelistxml.R
import com.example.gamelistxml.databinding.FragmentSettingsBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isIndonesian.collectLatest { isIndonesian ->
                binding.switchLanguage.setOnCheckedChangeListener(null)
                binding.switchLanguage.isChecked = isIndonesian
                binding.textCurrentLanguage.text = getString(
                    if (isIndonesian) R.string.Id_textId else R.string.En_textEn
                )
                binding.textSwitchLanguage.text = getString(
                    if (isIndonesian) R.string.language_textId else R.string.language_text
                )

                binding.switchLanguage.setOnCheckedChangeListener { _, isChecked ->
                    viewModel.toggleLanguage(isChecked)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}