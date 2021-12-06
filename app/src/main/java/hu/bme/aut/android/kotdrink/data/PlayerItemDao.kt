package hu.bme.aut.android.kotdrink.data

import androidx.room.*

@Dao
interface PlayerItemDao {
    @Query("SELECT * FROM playeritem")
    fun getAll(): List<PlayerItem>

    @Query("SELECT COUNT(id) FROM playeritem p WHERE p.is_playing = 1")
    fun getCountPlaying(): Int

    @Query("SELECT COUNT(id) FROM playeritem p WHERE p.played_last_game = 1")
    fun getCountPlayedLastGame(): Int

    @Query("SELECT EXISTS(SELECT * FROM playeritem)")
    fun isExists(): Boolean

    @Query("SELECT * FROM playeritem ORDER BY RANDOM() LIMIT 1")
    fun getRandom(): PlayerItem

    @Query("SELECT DISTINCT * FROM playeritem ORDER BY RANDOM() LIMIT 2")
    fun getTwoRandom(): List<PlayerItem>

    @Query("SELECT * FROM playeritem p WHERE p.sex = 'MALE' ORDER BY RANDOM() LIMIT 1")
    fun getRandomMale(): PlayerItem

    @Query("SELECT * FROM playeritem p WHERE p.sex = 'FEMALE' ORDER BY RANDOM() LIMIT 1")
    fun getRandomFemale(): PlayerItem

    @Query("SELECT * FROM playeritem p WHERE p.is_playing = 1")
    fun getAllPlayingThisGame(): List<PlayerItem>

    @Query("SELECT * FROM playeritem p WHERE p.played_last_game = 1")
    fun getAllPlayedLastGame(): List<PlayerItem>

    @Insert
    fun insert(playerItems: PlayerItem): Long

    @Update
    fun update(playerItem: PlayerItem)

    @Query("UPDATE playeritem SET played_last_game = 1 WHERE is_playing = 1")
    fun updatePlayedLastGame()

    @Query("UPDATE playeritem SET played_last_game = 0")
    fun resetPlayedLastGame()

    @Delete
    fun deleteItem(playerItem: PlayerItem)
}