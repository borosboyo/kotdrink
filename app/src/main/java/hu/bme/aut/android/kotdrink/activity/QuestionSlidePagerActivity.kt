package hu.bme.aut.android.kotdrink.activity

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import hu.bme.aut.android.kotdrink.adapter.QuestionSlidePagerAdapter
import hu.bme.aut.android.kotdrink.data.PlayerItem
import hu.bme.aut.android.kotdrink.data.PlayerItemDatabase
import hu.bme.aut.android.kotdrink.data.Question
import hu.bme.aut.android.kotdrink.data.Question.Type.*
import hu.bme.aut.android.kotdrink.data.QuestionDatabase
import hu.bme.aut.android.kotdrink.databinding.ActivityQuestionSlideBinding
import hu.bme.aut.android.kotdrink.transformer.ZoomOutPageTransformer


class QuestionSlidePagerActivity : FragmentActivity() {

    private lateinit var binding: ActivityQuestionSlideBinding
    private lateinit var questionDatabase: QuestionDatabase
    private lateinit var playerItemDatabase: PlayerItemDatabase
    private lateinit var currentCategory: Question.Type

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionSlideBinding.inflate(layoutInflater)
        setContentView(binding.root)
        populateDatabase()
        setQuestionsCategory()
        binding.mainViewPager.setPageTransformer(ZoomOutPageTransformer())
    }

    private fun setQuestionsCategory() {
        when(intent.getStringExtra("category").toString()){
            "WARMUP" -> currentCategory =  WARMUP
            "CLASSIC" -> currentCategory = CLASSIC
            "SPICY" ->  currentCategory = SPICY
            "HARDCORE" -> currentCategory = HARDCORE
        }
    }

    override fun onResume() {
        super.onResume()
        setAdapter()
    }

    private fun setAdapter() {
        val numberOfQuestionsInCategory : Int = questionDatabase.questionDao().getCountInCategory(currentCategory)
        val questionList : List<Question> = questionDatabase.questionDao().getRandomNotSeen(currentCategory,numberOfQuestionsInCategory)
        val questionsSlidePagerAdapter = QuestionSlidePagerAdapter(this, questionList)
        binding.mainViewPager.adapter = questionsSlidePagerAdapter
    }


    private fun populateDatabase(){
        questionDatabase = QuestionDatabase.getDatabase(applicationContext)
        playerItemDatabase = PlayerItemDatabase.getDatabase(applicationContext)
        initializeAllQuestions()
        initializeAllPlayers()
    }

    private fun initializeAllPlayers(){
        playerItemDatabase.playerItemDao().resetPlayedLastGame()
        playerItemDatabase.playerItemDao().updatePlayedLastGame()
    }

    //Termeszetesen vegtelen sokkal kreativabb kerdest is lehet irni
    private fun initializeAllQuestions(){
        playerItemDatabase = PlayerItemDatabase.getDatabase(applicationContext)
        if(questionDatabase.questionDao().getCountInCategory(WARMUP) ==  0){
            createWarmupQuestions()
        }
        if(questionDatabase.questionDao().getCountInCategory(CLASSIC) ==  0){
            createClassicQuestions()
        }
        if(questionDatabase.questionDao().getCountInCategory(SPICY) ==  0){
            createSpicyQuestions()
        }
        if(questionDatabase.questionDao().getCountInCategory(HARDCORE) ==  0){
            createHardcoreQuestions()
        }
    }

    private fun createWarmupQuestions(){
        injectQuestionToDb("Everyone drinks!", emptyList(), WARMUP)
        injectQuestionToDb("The youngest player drinks!", emptyList(), WARMUP)
        injectQuestionToDb("Everyone votes on who is most likely to throw a party for their pet. That person drinks", emptyList(), WARMUP)
        injectQuestionToDb("Touch your nose! The last player to do so drinks", emptyList(), WARMUP)
        injectQuestionToDb("Everyone drinks one for every year in university.", emptyList(), WARMUP)
    }

    private fun createClassicQuestions(){
        injectQuestionToDb("You have 30 seconds to make the person to your right laugh. If you fail, you drink. If you succeed, that person drinks.",
            listOf(playerItemDatabase.playerItemDao().getRandom()), CLASSIC)
        injectQuestionToDb("Choose a category. Take turns naming an item in chosen category. First to pause or miss must drink.",
            listOf(playerItemDatabase.playerItemDao().getRandom()), CLASSIC)
        injectQuestionToDb("Pick a player. This player drinks two.",
            listOf(playerItemDatabase.playerItemDao().getRandom()), CLASSIC)
        injectQuestionToDb("Make-a-rule! If someone breaks it, they must drink.",
            listOf(playerItemDatabase.playerItemDao().getRandom()), CLASSIC)
        injectQuestionToDb("Choose a word. Take rounds saying rhyming words. First to pause or miss must drink.",
            listOf(playerItemDatabase.playerItemDao().getRandom()), CLASSIC)
    }

    private fun createSpicyQuestions(){
        injectQuestionToDb("Everyone who paid for adult content must drink.", emptyList(), SPICY)
        injectQuestionToDb("Pick someone and guess the color of their underwear. If you guess correctly they drink, otherwise you do.",
            listOf(playerItemDatabase.playerItemDao().getRandom()), SPICY)
        injectQuestionToDb("Pick someone and close your eyes. Guess the body part they are touching you with. If you guess correctly they drink, otherwise you do.",
            listOf(playerItemDatabase.playerItemDao().getRandom()), SPICY)
        injectQuestionToDb("Armwrestle in underwear! Loser drinks.",
            playerItemDatabase.playerItemDao().getTwoRandom(), SPICY)
        injectQuestionToDb("Everyone who had a one-night-stand drinks.",
            emptyList(),SPICY)
    }

    private fun createHardcoreQuestions(){
        injectQuestionToDb("You are now drinking mates. Each of you has to drink when the other drinks.",
            playerItemDatabase.playerItemDao().getTwoRandom(),HARDCORE)
        injectQuestionToDb("Drink two and pick a player that drinks two too.",
            listOf(playerItemDatabase.playerItemDao().getRandom()), HARDCORE)
        injectQuestionToDb("The oldest player has to microwave their drink.",
            emptyList(), HARDCORE)
        injectQuestionToDb("Start drinking. Everyone must drink as long as you drink. You can stop at any time.",
            listOf(playerItemDatabase.playerItemDao().getRandom()), HARDCORE)
        injectQuestionToDb("Flip a coin. If it's heads, you empty your cup, if it's tails, everyone else does.",
            listOf(playerItemDatabase.playerItemDao().getRandom()), HARDCORE)
    }

    private fun injectQuestionToDb(text: String, players: List<PlayerItem>, type: Question.Type){
        val q = Question(null,text, players, type, false)
        questionDatabase.questionDao().insert(q)
    }
}