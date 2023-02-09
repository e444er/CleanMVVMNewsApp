package com.e444er.cleanmvvmnewsapp.presentation.topNews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.e444er.cleanmvvmnewsapp.R
import com.e444er.cleanmvvmnewsapp.databinding.FragmentTopNewsBinding
import com.e444er.cleanmvvmnewsapp.presentation.common.NewsAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopNewsFragment : Fragment(R.layout.fragment_top_news) {

    private var _binding: FragmentTopNewsBinding? = null
    private val binding get() = _binding!!

    private val topViewModel : TopViewModel by viewModels()

    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTopNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        setupObserver()

        newsAdapter.setOnItemClickListener {
           val action = TopNewsFragmentDirections.actionTopNewsFragmentToArticleFragment(
               articleargs = it
           )
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.topHeadLinesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
        }
    }

    private fun setupObserver() {
        topViewModel.state.observe(viewLifecycleOwner){
            if (it.isLoading){
                binding.contentProgressBar.show()
            }
            else if (it.errorMessage.isNotBlank()) {
                binding.contentProgressBar.hide()
                Snackbar.make(binding.root, it.errorMessage, Snackbar.LENGTH_LONG).show()
            } else if(it.data.isNotEmpty()) {
                binding.contentProgressBar.hide()
                newsAdapter.submitList(it.data)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}