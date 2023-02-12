package com.e444er.cleanmvvmnewsapp.data.remote.dto

import com.e444er.cleanmvvmnewsapp.domain.model.Article

data class ArticleDto(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: SourceDto,
    val title: String,
    val url: String,
    val urlToImage: String
)
fun List<ArticleDto>.toMovieList(): List<Article> {
    return map {
        Article(
            author = it.author,
            content = it.content,
            description = it.description,
            publishedAt = it.publishedAt,
            source = it.source.toSource(),
            title = it.title,
            url = it.url,
            urlToImage = it.urlToImage
        )
    }
}


