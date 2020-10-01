package com.tes.higo.higotes.ui.home

import android.os.Bundle
import android.view.View
import com.tes.higo.higotes.R
import com.tes.higo.higotes.base.BaseFragment
import com.tes.higo.higotes.databinding.FragmentHomeBinding
import com.tes.higo.higotes.ui.difficult.DifficultFragment
import com.tes.higo.higotes.ui.ranking.RankingFragment
import com.tes.higo.higotes.util.IOnBackPressed

class HomeFragment : BaseFragment<FragmentHomeBinding>(), IOnBackPressed {

    override fun layoutResId() = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAction()
    }

    private fun initAction() {
        binding.btnGames.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                moveToAnotherFragment(R.id.container_frame, DifficultFragment())
            }
        })

        binding.btnRanking.setOnClickListener {
            moveToAnotherFragment(R.id.container_frame, RankingFragment())
        }
    }

    override fun onBackPressed(): Boolean {
        activity?.finish()
        return true
    }


}