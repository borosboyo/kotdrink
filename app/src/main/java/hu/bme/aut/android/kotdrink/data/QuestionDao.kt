package hu.bme.aut.android.kotdrink.data

import androidx.room.*
@Dao
interface QuestionDao {
    @Query("SELECT * FROM question")
    fun getAll(): List<Question>

    @Query("SELECT COUNT(id) FROM question")
    fun getCount(): Int

    @Query("SELECT COUNT(id) FROM question q WHERE q.type = :currentType")
    fun getCountInCategory(currentType : Question.Type): Int

    @Query("SELECT * FROM question q WHERE q.seen = 0 AND q.type = :currentType ORDER BY RANDOM() LIMIT :numberOfQuestions")
    fun getRandomNotSeen(currentType : Question.Type, numberOfQuestions: Int): List<Question>

    @Query("SELECT * FROM question q WHERE q.type = :currentType")
    fun getTest(currentType : Question.Type): List<Question>

    @Insert
    fun insert(question: Question): Long

    @Update
    fun update(question: Question)

    @Delete
    fun deleteItem(question: Question)

}