package com.e444er.cleanmvvmnewsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.e444er.cleanmvvmnewsapp.data.local.dao.NewsArticleDao
import com.e444er.cleanmvvmnewsapp.data.local.entity.ArticleEntity

@Database(entities = [ArticleEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class NewsArticleDatabase : RoomDatabase() {

    abstract fun getNewsArticleDao() : NewsArticleDao

    companion object{
        const val DATABASE_NAME = "news_db"
    }

}