package com.tes.higo.higotes.ui.score

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tes.higo.higotes.data.db.QuizRepository
import com.tes.higo.higotes.model.db.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ScoreViewModel @Inject constructor(val quizRepository: QuizRepository) : ViewModel() {
    val compositeDisposable = CompositeDisposable()
    val loading = MutableLiveData<Boolean?>()
    val success = MutableLiveData<Boolean?>()
    val errorMessage = MutableLiveData<String?>()

    fun insertUser(user : User) {
        Log.d("lala", "jalan")
        loading.value = true
        compositeDisposable.add(
            quizRepository
                .insertUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("lala", "bgsd")
                    loading.value = false
                    success.value = true
                },
                    {
                        Log.d("lala", it.message!!)
                        loading.value = false
                        errorMessage.value = it.message
                        success.value = false
                    })
        )
    }

}