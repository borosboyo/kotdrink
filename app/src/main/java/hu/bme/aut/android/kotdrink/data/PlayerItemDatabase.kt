package hu.bme.aut.android.kotdrink.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [PlayerItem::class], version = 1)
@TypeConverters(value = [PlayerItem.Sex::class])
abstract class PlayerItemDatabase : RoomDatabase() {
    abstract fun playerItemDao(): PlayerItemDao

    companion object {
        fun getDatabase(applicationContext: Context): PlayerItemDatabase {
            return Room.databaseBuilder(
                applicationContext,
                PlayerItemDatabase::class.java,
                "player-list"
            ).allowMainThreadQueries().build()
        }
    }
}