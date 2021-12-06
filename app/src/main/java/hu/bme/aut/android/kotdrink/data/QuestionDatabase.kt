package hu.bme.aut.android.kotdrink.data
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Question::class], version = 2)
@TypeConverters(value = [QuestionConverter::class])
abstract class QuestionDatabase : RoomDatabase(){
    abstract fun questionDao(): QuestionDao

    companion object {
        fun getDatabase(applicationContext: Context): QuestionDatabase {
            return Room.databaseBuilder(
                applicationContext,
                QuestionDatabase::class.java,
                "question-list"
            ).allowMainThreadQueries().build()
        }
    }
}