package com.e444er.cleanmvvmnewsapp.presentation.searchNews

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.e444er.cleanmvvmnewsapp.R
import com.e444er.cleanmvvmnewsapp.databinding.FragmentSearchNewsBinding
import com.e444er.cleanmvvmnewsapp.presentation.common.NewsAdapter
import com.e444er.cleanmvvmnewsapp.presentation.common.SaveAdapter
import com.e444er.cleanmvvmnewsapp.presentation.topNews.TopNewsFragmentDirections
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {

    private var _binding: FragmentSearchNewsBinding? = null
    private val binding get() = _binding!!

    lateinit var newsAdapter: SaveAdapter
    private val searchNewsViewModel: SearchNewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupOnItemClickListener()
        setupSearch()
        setupSearchStateObserver()
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun setupSearch() {
        binding.searchSrcText.textChangeFlow()
            .debounce(300)
            .distinctUntilChanged()
            .mapLatest { searchNewsViewModel.searchNews(it) }
            .flowOn(Dispatchers.IO)
            .catch { Toast.makeText(requireContext(), "Search Error", Toast.LENGTH_LONG).show() }
            .onEach { Log.d("onEach", "onEach is : $it") }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setupSearchStateObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                launch {
//                    searchNewsViewModel.getTopHeadLines(binding.searchSrcText.text.toString()).collectLatest { pagingData ->
//                        newsAdapter.submitData(pagingData)
//                    }
//                }
                searchNewsViewModel.searchNewsState.collectLatest {
                    if (it.isLoading) {
                        binding.contentProgressBar.visibility = View.VISIBLE
                    } else if (it.errorMessage.isNotEmpty()) {
                        binding.contentProgressBar.visibility = View.GONE
                        Snackbar.make(binding.root, it.errorMessage, Snackbar.LENGTH_LONG).show()
                    } else {
                        binding.contentProgressBar.visibility = View.GONE
                        newsAdapter.submitList(it.articles)
                    }
                }
            }
        }
    }

    private fun setupOnItemClickListener() {
        newsAdapter.setOnItemClickListener {
            newsAdapter.setOnItemClickListener {
                val action = SearchNewsFragmentDirections.actionSearchNewsFragmentToArticleFragment(
                    articleargs = it
                )
                findNavController().navigate(action)
            }
        }
    }

    private fun setupRecyclerView() {
        newsAdapter = SaveAdapter()
        binding.searchRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}