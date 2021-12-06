package hu.bme.aut.android.kotdrink.data

import androidx.room.TypeConverter
import com.google.gson.Gson

class QuestionConverter {
    @TypeConverter
    fun listToJson(value: List<PlayerItem>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<PlayerItem>::class.java).toList()
}