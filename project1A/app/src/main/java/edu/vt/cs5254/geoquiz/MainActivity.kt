package edu.vt.cs5254.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import edu.vt.cs5254.geoquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    // name Gautham Gali
    // PID 906577777

    private lateinit var binding: ActivityMainBinding

    private lateinit var buttonList: List<Button>

    private val questionBank = Question(R.string.question_australia, listOf(
            Answer(R.string.answer_0_button, false),
            Answer(R.string.answer_1_button, true),
            Answer(R.string.answer_2_button, false),
            Answer(R.string.answer_3_button, false),
        ))

    private var currentIndex = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        buttonList = listOf(
            binding.answer0Button,
            binding.answer1Button,
            binding.answer2Button,
            binding.answer3Button
        )

        binding.lifelineButton.setOnClickListener {
            val incorrectAnswersToDisable = questionBank.answerList
                .filter { !it.isCorrect }
                .take(2)

            incorrectAnswersToDisable.forEach {
                it.isEnabled = false
                it.isSelected = false
            }

            binding.lifelineButton.isEnabled = false
            binding.resetButton.isEnabled = true

            updateView()
        }

        binding.resetButton.setOnClickListener {
            questionBank.answerList.forEach {
                it.isEnabled = true
                it.isSelected = false
            }

            binding.lifelineButton.isEnabled = true
            binding.resetButton.isEnabled = true

            updateView()
        }

        binding.lifelineButton.isEnabled = true
        binding.resetButton.isEnabled = true
        updateQuestion()

    }
    private fun updateQuestion() {
        binding.questionTextView.setText(questionBank.textResId)
        questionBank.answerList.zip(buttonList)
            .forEach() { (answer, button) ->
                button.setText(answer.textResId)
                button.setOnClickListener {
                    answer.isSelected = !answer.isSelected
                    questionBank.answerList.filter { it != answer }
                        .forEach() { deselectedAnswer ->
                            deselectedAnswer.isSelected = false
                        }
                    updateView()
                }
            }
        updateView()
    }
    private fun updateView() {
        questionBank.answerList.zip(buttonList)
            .forEach { (answer, button) ->
                button.isSelected = answer.isSelected
                button.isEnabled = answer.isEnabled
                button.updateColor()
            }
    }
}