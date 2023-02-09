package com.e444er.cleanmvvmnewsapp.data.utils

import com.google.gson.Gson
import java.lang.reflect.Type


class GsonParser(private val gson: Gson) : JsonParser {
    override fun <T> fromJson(jsonString: String, type: Type): T {
        return gson.fromJson(jsonString, type)
    }

    override fun <T> toJson(obj: T, type: Type): String {
        return gson.toJson(obj, type)
    }
}