package com.e444er.cleanmvvmnewsapp.presentation.save

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e444er.cleanmvvmnewsapp.R
import com.e444er.cleanmvvmnewsapp.databinding.FragmentSaveBinding
import com.e444er.cleanmvvmnewsapp.domain.model.Article
import com.e444er.cleanmvvmnewsapp.presentation.common.NewsAdapter
import com.e444er.cleanmvvmnewsapp.presentation.common.SaveAdapter
import com.e444er.cleanmvvmnewsapp.presentation.searchNews.SearchNewsFragmentDirections
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SaveFragment : Fragment(R.layout.fragment_save) {

    private var _binding: FragmentSaveBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var newsAdapter: SaveAdapter
    private val savedNewsViewModel: SavedNewsViewModel by viewModels()
    private var deletedArticle: Article? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSaveBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycleView()

        setupNewsStateObserver()

        ItemTouchHelper(getItemTouchHelper()).apply {
            attachToRecyclerView(binding.rvFav)
        }

        setOnItemClickNavigationToArticle()
    }

    private fun setOnItemClickNavigationToArticle() {
        newsAdapter.setOnItemClickListener {
            newsAdapter.setOnItemClickListener {
                val action = SaveFragmentDirections.actionSaveFragmentToArticleFragment(
                    articleargs = it
                )
                findNavController().navigate(action)
            }
        }
    }

    private fun getItemTouchHelper() = object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val article = newsAdapter.currentList[position]
            deleteArticle(article)
        }
    }

    private fun setupNewsStateObserver() {
        savedNewsViewModel.savedNewsState.observe(viewLifecycleOwner) {
            when (it) {
                is SavedNewsPageState.SavedNewsActionState -> {
                    if (it.articleDeleted) {
                        Snackbar.make(
                            binding.root,
                            "Article deleted successfully",
                            Snackbar.LENGTH_LONG
                        )
                            .setAction("Undo") {
                                deletedArticle?.let { article ->
                                    savedNewsViewModel.onEvent(
                                        SavedNewsEvent.UndoDeleteArticle(
                                            article
                                        )
                                    )
                                }
                            }.show()
                    } else if (it.articleDeletedUndo) {
                        Snackbar.make(binding.root, "Article delete undo", Snackbar.LENGTH_LONG)
                            .show()
                    }
                }
                is SavedNewsPageState.SavedNewsListState -> {
                    if (it.isLoading) {
                        binding.progressBar.visibility = View.VISIBLE
                    } else if (it.articles.isNotEmpty()) {
                        binding.progressBar.visibility = View.GONE
                        newsAdapter.submitList(it.articles)
                    }
                }
            }
        }
    }

    private fun deleteArticle(article: Article) {
        savedNewsViewModel.onEvent(SavedNewsEvent.DeleteArticle(article))
        deletedArticle = article
    }

    private fun setupRecycleView() {
        newsAdapter = SaveAdapter()
        binding.rvFav.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}