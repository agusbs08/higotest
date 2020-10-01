package com.tes.higo.higotes.ui.quiz

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.tes.higo.higotes.data.network.NetworkRepository
import com.tes.higo.higotes.model.network.Question
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class QuizViewModel @Inject constructor(val networkRepository: NetworkRepository) : ViewModel() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()
    var questions = MutableLiveData<MutableList<Question>?>()
    var loading = MutableLiveData<Boolean?>()
    var error = MutableLiveData<Boolean?>()
    var errorMessage = MutableLiveData<String?>()

    init {
        error.value = false
    }

    fun getQuestions(amount : Int, difficult : String, type : String) {
        loading.value = true
        compositeDisposable.add(
            networkRepository.getQuestions(amount, difficult, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    questionResponse ->
                    run {
                        Log.d("response", Gson().toJson(questionResponse))
                        loading.value = false
                        questions.value = questionResponse.results
                    }
                }, {throwable ->
                    run {
                        loading.value = false
                        errorMessage.value = throwable.message
                        error.value = true
                    }
                }))
    }

}