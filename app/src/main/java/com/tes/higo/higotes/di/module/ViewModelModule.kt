package com.tes.higo.higotes.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tes.higo.higotes.di.key.ViewModelKey
import com.tes.higo.higotes.ui.quiz.QuizViewModel
import com.tes.higo.higotes.ui.ranking.RankingViewModel
import com.tes.higo.higotes.ui.score.ScoreViewModel
import com.tes.higo.higotes.util.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory?): ViewModelProvider.Factory?

    @Binds
    @IntoMap
    @ViewModelKey(QuizViewModel::class)
    abstract fun bindQuizViewModel(quizViewModel: QuizViewModel?): ViewModel?

    @Binds
    @IntoMap
    @ViewModelKey(ScoreViewModel::class)
    abstract fun bindScoreViewModel(scoreViewModel: ScoreViewModel?): ViewModel?

    @Binds
    @IntoMap
    @ViewModelKey(RankingViewModel::class)
    abstract fun bindrankingModel(rankingViewModel: RankingViewModel?): ViewModel?
}