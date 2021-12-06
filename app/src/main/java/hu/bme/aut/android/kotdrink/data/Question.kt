package hu.bme.aut.android.kotdrink.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "question")
data class Question (
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    @ColumnInfo(name = "text")
    var text: String,
    @ColumnInfo(name = "players")
    var players: List<PlayerItem>,
    @ColumnInfo(name = "type")
    var type: Type,
    @ColumnInfo(name = "seen")
    var seen: Boolean
    ){
    enum class Type {
        WARMUP, CLASSIC, SPICY, HARDCORE;
        companion object {
            @JvmStatic
            @TypeConverter
            fun getByOrdinal(ordinal: Int): Type? {
                var ret: Type? = null
                for (cat in values()) {
                    if (cat.ordinal == ordinal) {
                        ret = cat
                        break
                    }
                }
                return ret
            }

            @JvmStatic
            @TypeConverter
            fun toInt(question: Type): Int {
                return question.ordinal
            }
        }
    }
}

