package hu.bme.aut.android.kotdrink.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.aut.android.kotdrink.data.Question
import hu.bme.aut.android.kotdrink.databinding.FragmentQuestionSlidePageBinding
class QuestionSlidePageFragment(randomQuestion: Question) : Fragment() {

    private lateinit var binding: FragmentQuestionSlidePageBinding
    private var currentQuestion = randomQuestion

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentQuestionSlidePageBinding.inflate(LayoutInflater.from(context))
        currentQuestion.seen = true

        if(!currentQuestion.players.isNullOrEmpty()){
            when(currentQuestion.players.size){
                1 -> {
                    val wholeText: String = currentQuestion.players.last().name + "! " + currentQuestion.text
                    binding.slideQuestionTv.text = wholeText

                }
                2 -> {
                    val wholeText: String = currentQuestion.players[0].name + " and " + currentQuestion.players[1].name + "! " + currentQuestion.text
                    binding.slideQuestionTv.text = wholeText
                }
            }
        }
        else{
            binding.slideQuestionTv.text = currentQuestion.text
        }
        return binding.root
    }
}