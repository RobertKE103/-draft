package com.example.networkinteractions.ui.bottomShetDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.networkinteractions.R
import com.example.networkinteractions.databinding.BottomSheetDialogSettingBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BottomSheetDialogSettingFragment : BottomSheetDialogFragment() {


    private var _binding: BottomSheetDialogSettingBinding? = null
    private val viewModel by viewModels<BottomSheetDialogSettingViewModel>()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = BottomSheetDialogSettingBinding.bind(
            inflater.inflate(R.layout.bottom_sheet_dialog_setting, container)
        )
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.listenerError.collectLatest {
                binding.editLayoutTextTheme.isErrorEnabled = it
            }
        }

        binding.saveTheme.setOnClickListener {
            viewModel.setTheme(binding.editTextTheme.text.toString())
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.closeDialog.collectLatest(::close)
        }
    }


    private fun close(isClose: Boolean) {
        if (!isClose) {
            this.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}