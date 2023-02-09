package com.e444er.cleanmvvmnewsapp.presentation.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.e444er.cleanmvvmnewsapp.databinding.FragmentArticleBinding
import com.e444er.cleanmvvmnewsapp.domain.model.Article
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    val binding get() = _binding!!

    private val args: ArticleFragmentArgs by navArgs()
    private val articleViewModel: ArticleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val article = args.articleargs

        Glide.with(binding.root).load(article.urlToImage).into(binding.imageView)
        binding.textTitle.text = article.title
        binding.textQ.text = article.description

        articleViewModel.articleState.observe(viewLifecycleOwner) {
            if (it.articleSaved) {
                binding.imageFavdis.isVisible = true
                binding.imageFavorite.isVisible = false
            } else {
                binding.imageFavdis.isVisible = false
                binding.imageFavorite.isVisible = true
            }
        }

        binding.imageFavorite.setOnClickListener {
            articleViewModel.onEvent(ArticleEvent.SaveArticle(article))
        }
        binding.imageFavdis.setOnClickListener {
            articleViewModel.onEvent(ArticleEvent.DeleteArticle(article))
        }

    }

    private fun liked(article: Article) {
        if (article.id == null) {
            binding.imageFavdis.isVisible = false
            binding.imageFavorite.isVisible = true
        } else {
            binding.imageFavdis.isVisible = true
            binding.imageFavorite.isVisible = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}