package com.example.networkinteractions.ui.setting

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.networkinteractions.R
import com.example.networkinteractions.databinding.FragmentSettingBinding
import com.example.networkinteractions.ui.SwitchMenu
import com.example.networkinteractions.ui.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SettingFragment : Fragment() {

    private val viewModel: SettingViewModel by viewModels()

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private var menu: SwitchMenu? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        menu = context as SwitchMenu
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menu?.visibleMenu(false)
        
        binding.intentMainFragment.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.main_fragment_container, MainFragment())
                .commit()
        }

        binding.themeNews.setOnCheckedChangeListener { radioGroup, i ->
            radioGroup.findViewById<RadioButton>(i).apply {
                viewModel.saveThemeSetting(text.toString())
                Toast.makeText(requireActivity(), text.toString(), Toast.LENGTH_LONG).show()
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.themeSettingFlow.collectLatest {
                setupRadioGroup(it)
                Log.d("dataStoreTest", it)
            }
        }
    }

    private fun setupRadioGroup(theme: String) {

        val id = when (theme) {

            getString(R.string.android) -> R.id.android_theme
            getString(R.string.sport) -> R.id.sports_theme
            getString(R.string.technology) -> R.id.technology_theme
            getString(R.string.politics) -> R.id.politics_theme
            else -> R.id.hz_theme

        }


        binding.themeNews.findViewById<RadioButton>(id).isChecked = true


    }


}