package com.tes.higo.higotes.ui.main

import com.tes.higo.higotes.ui.difficult.DifficultFragment
import com.tes.higo.higotes.ui.home.HomeFragment
import com.tes.higo.higotes.ui.quiz.QuizFragment
import com.tes.higo.higotes.ui.ranking.LevelRankingFragment
import com.tes.higo.higotes.ui.ranking.RankingFragment
import com.tes.higo.higotes.ui.score.ScoreFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainBindingFragmentModule {

    @ContributesAndroidInjector
    abstract fun provideHomeFragment(): HomeFragment?

    @ContributesAndroidInjector
    abstract fun provideDifficultFragment() : DifficultFragment?

    @ContributesAndroidInjector
    abstract fun provideQuizFragment() : QuizFragment?

    @ContributesAndroidInjector
    abstract fun provideScoreFragment() : ScoreFragment?

    @ContributesAndroidInjector
    abstract fun provideRankingFragment() : RankingFragment?

    @ContributesAndroidInjector
    abstract fun provideLevelRankingFragment() : LevelRankingFragment?
}