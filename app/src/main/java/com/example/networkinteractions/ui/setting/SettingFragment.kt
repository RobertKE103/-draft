package com.example.networkinteractions.ui.setting

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.networkinteractions.R
import com.example.networkinteractions.databinding.FragmentSettingBinding
import com.example.networkinteractions.ui.SwitchMenu
import com.example.networkinteractions.ui.bottomShetDialog.BottomSheetDialogSettingFragment
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


    private val bottomSheetDialog by lazy { BottomSheetDialogSettingFragment() }

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
                saveTheme(i)
            }
        }

        binding.themeNews.findViewById<RadioButton>(R.id.hz_theme).setOnClickListener {
            showBottomSheetDialog()
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.themeSettingFlow.collectLatest {
                setupRadioGroup(it)
                Log.d("dataStoreTest", it)
            }
        }
    }


    private fun showBottomSheetDialog(){
        bottomSheetDialog.show(requireActivity().supportFragmentManager, "tag")
    }
    private fun saveTheme(themeId: Int) {

        val radioButton = binding.themeNews.findViewById<RadioButton>(themeId)

        if (themeId != R.id.hz_theme) {
            viewModel.saveThemeSetting(radioButton.text.toString())
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

        if (id == R.id.hz_theme) {
            binding.themeNews.findViewById<RadioButton>(id).text = theme
        }

        binding.themeNews.findViewById<RadioButton>(id).isChecked = true


    }


}