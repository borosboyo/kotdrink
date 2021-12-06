package hu.bme.aut.android.kotdrink.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "playeritem")
data class PlayerItem(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "is_playing")
    var isPlaying: Boolean,
    @ColumnInfo(name = "played_last_game")
    var playedLastGame: Boolean,
    @ColumnInfo(name = "sex")
    var sex: Sex
) {

    enum class Sex {
        MALE, FEMALE;
        companion object {
            @JvmStatic
            @TypeConverter
            fun getByOrdinal(ordinal: Int): Sex? {
                var ret: Sex? = null
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
            fun toInt(sex: Sex): Int {
                return sex.ordinal
            }
        }
    }
}
