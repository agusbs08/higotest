package com.tes.higo.higotes.ui.ranking

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.tes.higo.higotes.R
import com.tes.higo.higotes.databinding.FragmentLevelRankingBinding
import com.tes.higo.higotes.di.binding.BindingFragment
import com.tes.higo.higotes.model.db.User

class LevelRankingFragment : BindingFragment<FragmentLevelRankingBinding, RankingViewModel>() {

    private var difficult : String? = null
    private val users : MutableList<User> = mutableListOf()
    private lateinit var adapter: LevelRankingRecyclerViewAdapter

    override fun layoutResId() = R.layout.fragment_level_ranking

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createViedModel(RankingViewModel::class.java)

        initData()
        initView()
        observableViewModel()
        viewModel.getRankingUser(difficult!!)
    }

    private fun initData() {
        if(arguments != null ) {
            difficult = arguments?.getString("difficult")
        } else {
            difficult = "easy"
        }
    }

    private fun initView() {
        adapter = LevelRankingRecyclerViewAdapter(users)
        binding.rvRanking.layoutManager = LinearLayoutManager(context)
        binding.rvRanking.adapter = adapter
    }

    override fun observableViewModel() {
        viewModel.users.observe(viewLifecycleOwner, Observer {
            users.addAll(it)
            adapter.notifyDataSetChanged()
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if(it!!) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            if(it!!) {
                onMessage(viewModel.errorMessage.value)
                viewModel.error.value = false
            }
        })
    }

}