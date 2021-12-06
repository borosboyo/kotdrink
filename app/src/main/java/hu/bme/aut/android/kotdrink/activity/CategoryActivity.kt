package hu.bme.aut.android.kotdrink.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.android.kotdrink.data.Question
import hu.bme.aut.android.kotdrink.data.Question.Type.*
import hu.bme.aut.android.kotdrink.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClickListeners()
    }

    private fun setupClickListeners(){
        val cat = Intent(this, QuestionSlidePagerActivity::class.java)
        binding.btnWarmup.setOnClickListener{
            startActivityWithCurrentCategory(cat, WARMUP)
        }
        binding.btnClassic.setOnClickListener{
            startActivityWithCurrentCategory(cat, CLASSIC)
        }
        binding.btnSpicy.setOnClickListener{
            startActivityWithCurrentCategory(cat, SPICY)
        }
        binding.btnHardcore.setOnClickListener{
            startActivityWithCurrentCategory(cat, HARDCORE)
        }
        binding.back.setOnClickListener {
            super.onBackPressed()
        }
    }

    private fun startActivityWithCurrentCategory(cat: Intent, category: Question.Type) {
        cat.putExtra("category", category.toString())
        startActivity(cat)
    }
}