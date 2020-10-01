package com.tes.higo.higotes.ui.ranking

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tes.higo.higotes.data.db.QuizRepository
import com.tes.higo.higotes.model.db.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RankingViewModel @Inject constructor(val quizRepository: QuizRepository) : ViewModel() {

    val compositeDisposable = CompositeDisposable()
    val users = MutableLiveData<MutableList<User>>()
    val loading = MutableLiveData<Boolean?>()
    val error = MutableLiveData<Boolean?>()
    val errorMessage = MutableLiveData<String?>()

    init {
        error.value = false
    }

    fun getRankingUser(difficult : String) {
        loading.value = true
        compositeDisposable.add(
            quizRepository
                .getUsersRanking(difficult)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {it ->
                        run {
                            loading.value = false
                            users.value = it.toMutableList()
                        }
                    },
                    { throwable ->
                        run {
                            loading.value = false
                            errorMessage.value = throwable.message
                            error.value = true
                        }

                    })
        )
    }
}