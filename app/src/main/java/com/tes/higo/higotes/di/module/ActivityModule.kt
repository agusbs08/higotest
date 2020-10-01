package com.tes.higo.higotes.di.module

import com.tes.higo.higotes.ui.main.MainActivity
import com.tes.higo.higotes.ui.main.MainBindingFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [MainBindingFragmentModule::class])
    abstract fun bindMainActivity(): MainActivity?

}