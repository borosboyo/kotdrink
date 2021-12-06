package hu.bme.aut.android.kotdrink.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import hu.bme.aut.android.kotdrink.data.Question
import hu.bme.aut.android.kotdrink.fragment.QuestionSlidePageFragment


class QuestionSlidePagerAdapter(
    fragmentActivity: FragmentActivity,
    randomQuestionList: List<Question>
) : FragmentStateAdapter(fragmentActivity) {

    private var questionList = randomQuestionList

    override fun getItemCount(): Int = questionList.size
    override fun createFragment(position: Int): Fragment {
        return QuestionSlidePageFragment(questionList[position])
    }
}