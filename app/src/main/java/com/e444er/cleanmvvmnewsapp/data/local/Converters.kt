package com.e444er.cleanmvvmnewsapp.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.e444er.cleanmvvmnewsapp.data.utils.JsonParser
import com.e444er.cleanmvvmnewsapp.domain.model.Source
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
) {

    @TypeConverter
    fun sourceToJsonString(source: Source): String {
        return jsonParser.toJson<Source>(
            source,
            object : TypeToken<Source>(){}.type
        ) ?: ""
    }

    @TypeConverter
    fun jsonStringToSource(jsonString: String) : Source {
        return jsonParser.fromJson<Source>(
            jsonString,
            object : TypeToken<Source>(){}.type
        ) ?: Source(null, null)
    }


}