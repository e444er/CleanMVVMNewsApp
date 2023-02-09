package com.e444er.cleanmvvmnewsapp.data.utils

import java.lang.reflect.Type

interface JsonParser {

    fun <T> fromJson(jsonString: String, type: Type) : T?

    fun <T> toJson(obj : T, type: Type) : String?
}