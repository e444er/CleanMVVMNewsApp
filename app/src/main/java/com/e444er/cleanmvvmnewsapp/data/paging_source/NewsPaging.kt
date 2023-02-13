package com.e444er.cleanmvvmnewsapp.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.e444er.cleanmvvmnewsapp.data.remote.api.NewsApi
import com.e444er.cleanmvvmnewsapp.data.remote.dto.toMovieList
import com.e444er.cleanmvvmnewsapp.data.utils.Constants
import com.e444er.cleanmvvmnewsapp.data.utils.Constants.Companion.API_KEY
import com.e444er.cleanmvvmnewsapp.domain.model.Article
import javax.inject.Inject

class NewsPaging @Inject constructor(
    private val newsApi: NewsApi,
    private val language: String,
) : PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {

        val nextPage = params.key ?: Constants.STARTING_PAGE

        return  try {
            val apiResponse = newsApi.getTopHeadlines(
                country = language,
                category = "science",
                page = nextPage,
                apiKey = API_KEY
            )
            LoadResult.Page(
                data = apiResponse.articles.toMovieList(),
                prevKey = if (nextPage == 1) null else nextPage.minus(1),
                nextKey = if (nextPage < apiResponse.totalResults) nextPage.plus(1) else null
            )

        } catch (e: Exception) {
            LoadResult.Error(throwable = e)
        }
    }

}