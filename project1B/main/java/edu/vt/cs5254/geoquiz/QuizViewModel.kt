package edu.vt.cs5254.geoquiz

import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {
    var currIndex = 0

    val questionBank = listOf(
        Question(R.string.question_1, listOf(
            Answer(R.string.q1answer_0_button, true),
            Answer(R.string.q1answer_1_button, false),
            Answer(R.string.q1answer_2_button, false),
            Answer(R.string.q1answer_3_button, false)
        )
        ),
        Question(R.string.question_2, listOf(
            Answer(R.string.q2answer_0_button, false),
            Answer(R.string.q2answer_1_button, true),
            Answer(R.string.q2answer_2_button, false),
            Answer(R.string.q2answer_3_button, false)
        )
        ),
        Question(R.string.question_3, listOf(
            Answer(R.string.q3answer_0_button, false),
            Answer(R.string.q3answer_1_button, false),
            Answer(R.string.q3answer_2_button, true),
            Answer(R.string.q3answer_3_button, false)
        )
        ),
        Question(R.string.question_4, listOf(
            Answer(R.string.q4answer_0_button, false),
            Answer(R.string.q4answer_1_button, false),
            Answer(R.string.q4answer_2_button, false),
            Answer(R.string.q4answer_3_button, true)
        )
        ),
    )

    val answerList get() = questionBank[currIndex].answerList
    val qtID get() = questionBank[currIndex].textResId
    val questionBankSize = questionBank.size
    var correctAnswers: Int = 0
    var hintsUsed = 0

    fun moveToNextQuestion() {
        currIndex = (currIndex + 1) % questionBank.size
    }

    fun hasMoreQuestions(): Boolean {
        return currIndex < questionBank.size - 1
    }

    fun checkHintUsed() {
        hintsUsed += 1
    }

    fun correctAnswersCount() {
        correctAnswers += 1
    }

    fun resetAll(){
        correctAnswers = 0
        hintsUsed = 0
    }

    fun resetAnswers() {
        val resetQuestionBank = questionBank
            .flatMap() { (questions, answers) ->
                answers.toList()
            }
        resetQuestionBank.forEach() {
            it.isEnabled = true
            it.isSelected = false
        }
    }
}