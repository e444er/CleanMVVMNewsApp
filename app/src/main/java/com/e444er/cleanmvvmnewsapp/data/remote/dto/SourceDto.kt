package com.e444er.cleanmvvmnewsapp.data.remote.dto

import com.e444er.cleanmvvmnewsapp.domain.model.Source

data class SourceDto(
    val id: String,
    val name: String
) {
    fun toSource() : Source = Source(
        id = id,
        name = name
    )
}