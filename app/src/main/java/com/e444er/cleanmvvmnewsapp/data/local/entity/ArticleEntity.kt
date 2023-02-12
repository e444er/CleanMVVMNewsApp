package com.e444er.cleanmvvmnewsapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.e444er.cleanmvvmnewsapp.domain.model.Article
import com.e444er.cleanmvvmnewsapp.domain.model.Source

@Entity
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val author: String? = "",
    val content: String? = "",
    val description: String? = "",
    val publishedAt: String? = "",
    val source: Source?,
    val title: String? = "",
    val url: String = "",
    val urlToImage: String? = ""
) {
    fun toArticle() = Article(
        id = id,
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = source,
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}

fun Article.toArticleEntity() = ArticleEntity(
    id = id,
    author = author,
    content = content,
    description = description,
    publishedAt = publishedAt,
    source = source,
    title = title,
    url = url,
    urlToImage = urlToImage
)
