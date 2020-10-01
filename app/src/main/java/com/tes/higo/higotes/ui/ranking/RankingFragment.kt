package com.tes.higo.higotes.ui.ranking

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.tes.higo.higotes.R
import com.tes.higo.higotes.databinding.FragmentRankingBinding
import com.tes.higo.higotes.base.BaseFragment
import dagger.android.support.DaggerFragment

class RankingFragment : BaseFragment<FragmentRankingBinding>() {

    override fun layoutResId() = R.layout.fragment_ranking

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar((activity as AppCompatActivity).findViewById(R.id.toolbar))
        (activity as AppCompatActivity).supportActionBar?.title = "Ranking"

        initAction()
    }

    private fun initAction() {
        binding.bnvRanking.setOnNavigationItemSelectedListener {
            val fragment = LevelRankingFragment()
            val bundle = Bundle()
            val key = "difficult"

            when(it.itemId) {
                R.id.item_easy -> {
                    bundle.putString(key, "easy")
                }
                R.id.item_medium -> {
                    bundle.putString(key, "medium")
                }
                R.id.item_hard -> {
                    bundle.putString(key, "hard")
                }
            }

            setFragment(fragment, bundle)

            true
        }

        binding.bnvRanking.selectedItemId = R.id.item_easy
    }

    private fun setFragment(fragment : DaggerFragment, bundle: Bundle) {
        fragment.arguments = bundle
        childFragmentManager
            .beginTransaction()
            .replace(R.id.frame_ranking, fragment, fragment::class.simpleName)
            .addToBackStack(null)
            .commit()
    }

}