package com.tes.higo.higotes.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.tes.higo.higotes.R
import com.tes.higo.higotes.base.BaseFragment
import com.tes.higo.higotes.ui.home.HomeFragment
import com.tes.higo.higotes.util.IOnBackPressed
import dagger.android.support.DaggerAppCompatActivity


class MainActivity : DaggerAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container_frame, HomeFragment(), HomeFragment::class.simpleName)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {

        val fragments: List<Fragment> = supportFragmentManager.fragments
        for (f in fragments) {
            if (f is IOnBackPressed){
                (f as IOnBackPressed).onBackPressed()
                return
            }
        }

        if(supportFragmentManager?.backStackEntryCount > 0) {
            supportFragmentManager?.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

}