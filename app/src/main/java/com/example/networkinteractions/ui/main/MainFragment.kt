package com.example.networkinteractions.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.networkinteractions.R
import com.example.networkinteractions.databinding.FragmentMainBinding
import com.example.networkinteractions.ui.setting.SettingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
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

        openNewFragment()

        observerVisibilityET()



        setupScrollListener()


        binding.swipeLayout.setOnRefreshListener {
            binding.swipeLayout.isRefreshing = false
        }


    }

    private fun setupScrollListener() {
        binding.rvNews.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy >= 10) {
                    newsViewModel.setupVisibleET(false)
                }

            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                val manager = (recyclerView.layoutManager) as LinearLayoutManager

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE &&
                    manager.findFirstVisibleItemPosition() == 0
                ) {
                    newsViewModel.setupVisibleET(true)
                }
            }
        })
    }

    private fun observerVisibilityET() {
        viewLifecycleOwner.lifecycleScope.launch {
            newsViewModel.isVisibleEditText.collectLatest(::setupVisibleEditText)
        }
    }

    private fun openNewFragment() {
        binding.transitionSettingFragment.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, SettingFragment())
                .commit()

        }
    }

    private fun setupVisibleEditText(isVisibility: Boolean) {
        binding.transitionSettingFragment.visibility = if (isVisibility) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun observerNewsDataFromNetwork() {
        viewLifecycleOwner.lifecycleScope.launch {
            newsViewModel.news.collectLatest(adapter::submitData)
        }
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