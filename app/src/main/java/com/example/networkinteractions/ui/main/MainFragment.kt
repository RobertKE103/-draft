package com.example.networkinteractions.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.example.networkinteractions.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val newsViewModel: MainViewModel by viewModels()
    private val adapter by lazy { NewsListAdapter() }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRv()
        observerNewsDataFromNetwork()

    }

    private fun observerNewsDataFromNetwork() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                newsViewModel.news.collectLatest(adapter::submitData)
            }
        }

        newsViewModel.theme
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .launchIn(lifecycleScope)
    }

    private fun setupRv() {
        val rv = binding.rvNews
        rv.adapter = adapter.withLoadStateHeaderAndFooter(
            header = NewsLoaderStateAdapter(),
            footer = NewsLoaderStateAdapter()
        )
        adapter.addLoadStateListener {
            binding.rvNews.isVisible = it.refresh != LoadState.Loading
            binding.progress.isVisible = it.refresh == LoadState.Loading
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}