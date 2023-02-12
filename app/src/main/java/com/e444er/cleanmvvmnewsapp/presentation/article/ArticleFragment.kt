package com.e444er.cleanmvvmnewsapp.presentation.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

    private lateinit var articleModel: Article

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
//        setLiked(article)

        Glide.with(binding.root).load(article.urlToImage).into(binding.imageView)
        binding.textTitle.text = article.title
        binding.textQ.text = article.description

        articleViewModel.articleState.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "ok", Toast.LENGTH_SHORT).show()
        }

        binding.checkLiked.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                articleViewModel.onEvent(ArticleEvent.SaveArticle(article))
            }else {
                articleViewModel.onEvent(ArticleEvent.DeleteArticle(article))
            }
        }

//        binding.imageFavorite.setOnClickListener {
//            if (it.callOnClick()){
//
//            }
//            articleViewModel.onEvent(ArticleEvent.SaveArticle(article))
////            binding.imageFavdis.setImageResource(R.drawable.saved)
//            binding.imageFavdis.isVisible = true
//            binding.imageFavorite.isVisible = false
//        }
//        binding.imageFavdis.setOnClickListener {
//            articleViewModel.onEvent(ArticleEvent.DeleteArticle(article))
//            binding.imageFavdis.isVisible = false
//            binding.imageFavorite.isVisible = true
//        }
    }

//    private fun setLiked(id: Article) {
//        if (id.id != null) {
//            binding.imageFavdis.isVisible = true
//            binding.imageFavorite.isVisible = false
//        } else {
//            binding.imageFavdis.isVisible = false
//            binding.imageFavorite.isVisible = true
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}