package com.tes.higo.higotes.ui.quiz

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Html
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.tes.higo.higotes.R
import com.tes.higo.higotes.databinding.FragmentQuizBinding
import com.tes.higo.higotes.di.binding.BindingFragment
import com.tes.higo.higotes.model.network.Question
import com.tes.higo.higotes.model.state.Answer
import com.tes.higo.higotes.ui.score.ScoreFragment

class QuizFragment : BindingFragment<FragmentQuizBinding, QuizViewModel>() {

    private var questions : MutableList<Question>? = mutableListOf()
    private val type = "multiple"
    private val key = "difficult"
    private val defaultDifficult = "easy"
    private lateinit var difficult : String
    private val timeToAnswer = 8 * 1000L   // milis
    private var timeLeft = 0f
    private var position = 0
    private lateinit var correctStrAnswer : String

    private var score = 0
    private val amount = 10
    private var trueAnswerCount = 0
    private var falseAnswerCount = 0
    private var skipAnswerCount = 0

    private var timeOutRemoveTimer = object : CountDownTimer(timeToAnswer, 10) {
        override fun onFinish() {
            binding.circularProgressBar.progress = 1f
            onAfterSelectAnswer(null)
        }

        override fun onTick(millisUntilFinished: Long) {
            timeLeft = (millisUntilFinished / 1000).toFloat()
            binding.circularProgressBar.progress = (timeToAnswer - millisUntilFinished).toFloat() / timeToAnswer
        }

    }

    override fun layoutResId() = R.layout.fragment_quiz

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(arguments != null) {
            difficult = arguments!!.getString(key, defaultDifficult)
        } else {
            difficult = defaultDifficult
        }

        createViedModel(QuizViewModel::class.java)
        observableViewModel()
        initAction()
        viewModel.getQuestions(amount, difficult, type)

    }

    override fun observableViewModel() {
        viewModel.error.observe(viewLifecycleOwner, Observer {
            if(it!!) {
                binding.tvErrorMsg.text = viewModel.errorMessage.value
                binding.mainContainer.visibility = View.GONE
                binding.linearError.visibility = View.VISIBLE
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if(it!!) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        })

        viewModel.questions.observe(viewLifecycleOwner, Observer {
            Log.d("response", Gson().toJson(it))
            binding.mainContainer.visibility = View.VISIBLE
            binding.linearError.visibility = View.GONE
            this.questions = it
            nextQuestion()
        })
    }

    private fun initAction() {
        binding.btnRetry.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                binding.linearError.visibility = View.GONE
                viewModel.getQuestions(amount, difficult, type)
            }
        })

        binding.btnAnswerA.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                onAfterSelectAnswer(binding.tvAnswerA.text.toString())
            }
        })

        binding.btnAnswerB.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                onAfterSelectAnswer(binding.tvAnswerB.text.toString())
            }
        })

        binding.btnAnswerC.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                onAfterSelectAnswer(binding.tvAnswerC.text.toString())
            }
        })

        binding.btnAnswerD.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                onAfterSelectAnswer(binding.tvAnswerD.text.toString())
            }
        })

    }

    private fun onAfterSelectAnswer(answer: String?) {
        position += 1
        timeOutRemoveTimer.cancel()
        checkAnswer(answer)
        if(position == questions?.size) {
            finishQuiz()
        } else {
            nextQuestion()
        }
    }

    private fun checkAnswer(strAnswer : String?) {
        val answer : Answer
        if(strAnswer == null) {
            skipAnswerCount += 1
        } else if(strAnswer.equals(correctStrAnswer)) {
            trueAnswerCount += 1
            score += 100 + (timeLeft.toInt() * 5)
        } else {
            falseAnswerCount += 1
        }
    }

    private fun nextQuestion() {
        binding.tvQuestionNumber.text = (position + 1).toString()
        val question = questions?.get(position)

        binding.tvQuestion.text = Html.fromHtml(question?.question)
        val listAnswer = question?.incorrectAnswers?.toMutableList()

        correctStrAnswer = question?.correctAnswer!!
        listAnswer?.add(correctStrAnswer)

        listAnswer?.shuffle()

        binding.tvAnswerA.text = Html.fromHtml(listAnswer?.get(0))
        binding.tvAnswerB.text = Html.fromHtml(listAnswer?.get(1))
        binding.tvAnswerC.text = Html.fromHtml(listAnswer?.get(2))
        binding.tvAnswerD.text = Html.fromHtml(listAnswer?.get(3))

        timeOutRemoveTimer.start()
    }

    private fun finishQuiz() {
        val answer = Answer(trueAnswerCount, falseAnswerCount, skipAnswerCount, score, difficult)
        val fragment = ScoreFragment()
        val bundle = Bundle()
        bundle.putSerializable("answer", answer)
        fragment.arguments = bundle
        moveToAnotherFragment(R.id.container_frame, fragment)
    }

}