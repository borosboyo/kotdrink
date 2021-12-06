package hu.bme.aut.android.kotdrink.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.android.kotdrink.R.string
import hu.bme.aut.android.kotdrink.data.PlayerItem
import hu.bme.aut.android.kotdrink.data.PlayerItemDatabase
import hu.bme.aut.android.kotdrink.databinding.ActivityMainMenuBinding
import java.text.SimpleDateFormat
import java.util.*


//Sajnos nem vagyok frond-end developer, elnezest a szornyu UI-ert. A back-end legalabb mukodik :)
class MainMenuActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainMenuBinding
    private lateinit var pref : SharedPreferences
    private lateinit var editor : SharedPreferences.Editor
    private lateinit var playerItemDatabase : PlayerItemDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupButtonListeners()
    }

    override fun onResume() {
        super.onResume()
        playerItemDatabase = PlayerItemDatabase.getDatabase(applicationContext)
        setupLastPlayedDateText()
        setupLastPlayedWithText()
    }

    private fun setupLastPlayedWithText(){
        binding.textView4.text = ""
        val playersWhoPlayedLastGame: List<PlayerItem> = playerItemDatabase.playerItemDao().getAllPlayedLastGame()
        if(playersWhoPlayedLastGame.isEmpty()){
            binding.textView4.text = getString(string.nobody)
        }
        else{
            createPlayerList(playersWhoPlayedLastGame)
        }
    }

    private fun createPlayerList(playersWhoPlayedLastGame: List<PlayerItem>) {
        for (p: PlayerItem in playersWhoPlayedLastGame) {
            val previousText: String = binding.textView4.text.toString()
            if (p != playersWhoPlayedLastGame.last())
                binding.textView4.text = previousText + p.name + ", "
            else
                binding.textView4.text = previousText + p.name
        }
    }

    private fun setupLastPlayedDateText(){
        pref = applicationContext.getSharedPreferences("kotdrink-prefs",0)
        val sdf = SimpleDateFormat("yyyy/M/dd hh:mm")
        val currentDate: String

        if(getFirstRun()){
            currentDate = "Never played before :("
            setAlreadyRan()
        }
        else
            currentDate =  sdf.format(Date())

        binding.textView2.text = currentDate
    }

    private fun getFirstRun(): Boolean{
        return pref.getBoolean("firstRun",true)
    }

    private fun setAlreadyRan(){
        editor =  pref.edit()
        editor.putBoolean("firstRun", false)
        editor.commit()
    }

    private fun setupButtonListeners(){
        binding.btnStart.setOnClickListener{
            checkPlayerMinimum()
        }
        binding.btnAddPlayer.setOnClickListener{
            startActivity(Intent (this, AddPlayerActivity::class.java))
        }
    }

    private fun checkPlayerMinimum(){
        if(playerItemDatabase.playerItemDao().getCountPlaying() >= 2){
            startActivity(Intent (this, CategoryActivity::class.java))
        }
        else{
            Snackbar.make(binding.root, string.warn_message, Snackbar.LENGTH_LONG)
                .show()
        }
    }

}