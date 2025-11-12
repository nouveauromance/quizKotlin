package com.example.quizapp

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.databinding.ActivityMainBinding
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val questions = arrayOf(
        "Qual das alternativas é a forma correta de declarar uma variável imutável?",
        "O que a palavra-chave data faz em uma classe?",
        "Qual é a sintaxe correta para criar uma função que recebe dois inteiros e retorna a soma deles?",
        "Qual é a principal diferença entre val e var?",
        "Qual operador é usado para verificar se uma variável é null?"
    )

    private val options = arrayOf(
        arrayOf("let", "val", "var"),
        arrayOf(
            "Gera automaticamente os métodos toString(), equals() e hashCode()",
            "Cria uma classe que não pode ser modificada",
            "Cria um tipo de dado personalizado"
        ),
        arrayOf(
            "fun soma(a: Int, b: Int) -> Int { return a + b }",
            "fun soma(a: Int, b: Int): Int { return a + b }",
            "def soma(a: Int, b: Int): Int { return a + b }"
        ),
        arrayOf(
            "val é imutável e var é mutável",
            "val é usado para tipos primitivos e var para objetos",
            "var é imutável e val é mutável"
        ),
        arrayOf("?.", "!!", "?=")
    )

    private val correctAnswers = arrayOf(1, 0, 1, 0, 0)

    private var currentQuestionIndex = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        displayQuestion()

        binding.option1Button.setOnClickListener { checkAnswer(0) }
        binding.option2Button.setOnClickListener { checkAnswer(1) }
        binding.option3Button.setOnClickListener { checkAnswer(2) }
        binding.restartButton.setOnClickListener { restartQuiz() }
    }

    private fun correctButtonColors(buttonIndex: Int) {
        when (buttonIndex) {
            0 -> binding.option1Button.setBackgroundColor(Color.GREEN)
            1 -> binding.option2Button.setBackgroundColor(Color.GREEN)
            2 -> binding.option3Button.setBackgroundColor(Color.GREEN)
        }
    }

    private fun wrongButtonColors(buttonIndex: Int) {
        when (buttonIndex) {
            0 -> binding.option1Button.setBackgroundColor(Color.RED)
            1 -> binding.option2Button.setBackgroundColor(Color.RED)
            2 -> binding.option3Button.setBackgroundColor(Color.RED)
        }
    }

    private fun resetButtonColors() {
        binding.option1Button.setBackgroundColor(Color.rgb(50, 59, 96))
        binding.option2Button.setBackgroundColor(Color.rgb(50, 59, 96))
        binding.option3Button.setBackgroundColor(Color.rgb(50, 59, 96))
    }

    private fun showResults() {
        Toast.makeText(this, "Your score: $score out of ${questions.size}", Toast.LENGTH_LONG).show()
        binding.restartButton.isEnabled = true
    }

    private fun displayQuestion() {
        binding.questionText.text = questions[currentQuestionIndex]
        binding.option1Button.text = options[currentQuestionIndex][0]
        binding.option2Button.text = options[currentQuestionIndex][1]
        binding.option3Button.text = options[currentQuestionIndex][2]
        resetButtonColors()
    }

    private fun checkAnswer(selectedAnswerIndex: Int) {
        val correctAnswerIndex = correctAnswers[currentQuestionIndex]

        if (selectedAnswerIndex == correctAnswerIndex) {
            score++
            correctButtonColors(selectedAnswerIndex)
        } else {
            wrongButtonColors(selectedAnswerIndex)
            correctButtonColors(correctAnswerIndex)
        }

        binding.option1Button.isEnabled = false
        binding.option2Button.isEnabled = false
        binding.option3Button.isEnabled = false

        binding.questionText.postDelayed({
            if (currentQuestionIndex < questions.size - 1) {
                currentQuestionIndex++
                displayQuestion()
                binding.option1Button.isEnabled = true
                binding.option2Button.isEnabled = true
                binding.option3Button.isEnabled = true
            } else {
                showResults()
            }
        }, 1000)
    }

    private fun restartQuiz() {
        currentQuestionIndex = 0
        score = 0
        resetButtonColors()
        displayQuestion()
        binding.restartButton.isEnabled = false
    }
}
