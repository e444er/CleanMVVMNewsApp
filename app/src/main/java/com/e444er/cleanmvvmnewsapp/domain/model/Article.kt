package com.e444er.cleanmvvmnewsapp.domain.model

import android.os.Parcelable
import com.e444er.cleanmvvmnewsapp.data.local.entity.ArticleEntity
import com.e444er.cleanmvvmnewsapp.data.remote.dto.ArticleDto
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Article(
    val id: Int? = null,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: @RawValue Source?,
    val title: String?,
    val url: String,
    val urlToImage: String?
) : Parcelable

fun Article.toArticleEntity() : ArticleEntity = ArticleEntity(
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
