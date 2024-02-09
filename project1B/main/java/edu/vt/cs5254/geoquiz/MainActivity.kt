package edu.vt.cs5254.geoquiz

import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import edu.vt.cs5254.geoquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    // name Gautham Gali
    // PID 906577777

    private lateinit var binding: ActivityMainBinding

    private lateinit var buttonList: List<Button>
    private val quizVM: QuizViewModel by viewModels()
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

        binding.hintButton.setOnClickListener {
            quizVM.answerList
                .filter {
                    it.isEnabled &&
                            !it.isCorrect
                }
                .random()
                .let {
                    it.isEnabled = false
                    it.isSelected = false
                }
            quizVM.checkHintUsed()
            updateView()
        }

        binding.submitButton.setOnClickListener {
            quizVM.answerList.zip(buttonList)
                .forEach() { (answer, button) ->
                    if(button.isSelected && answer.isCorrect) {
                        quizVM.correctAnswersCount()
                    }
                }
            if(quizVM.hasMoreQuestions()) {
                quizVM.moveToNextQuestion()
                updateQuestion()
            } else {
                quizVM.resetAnswers()
                quizVM.resetAll()
                quizVM.moveToNextQuestion()
                updateQuestion()
            }
        }
        updateQuestion()

    }
    private fun updateQuestion() {
        binding.questionTextView.setText(quizVM.qtID)
        quizVM.answerList.zip(buttonList)
            .forEach() { (answer, button) ->
                button.setText(answer.textResId)
                button.setOnClickListener {
                    answer.isSelected = !answer.isSelected
                    quizVM.answerList.filter {it != answer}
                        .forEach() { deselectedAnswer ->
                            deselectedAnswer.isSelected = false
                        }
                    updateView()
                }
            }
        updateView()
    }
    private fun updateView() {
        quizVM.answerList.zip(buttonList)
            .forEach { (answer, button) ->
                button.isSelected = answer.isSelected
                button.isEnabled = answer.isEnabled
                button.updateColor()
            }
        binding.hintButton.isEnabled = quizVM.answerList.any { it.isEnabled && !it.isCorrect }
        binding.submitButton.isEnabled = quizVM.answerList.any { it.isSelected }
    }
}