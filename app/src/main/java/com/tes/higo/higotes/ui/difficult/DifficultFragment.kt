package com.tes.higo.higotes.ui.difficult

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.tes.higo.higotes.R
import com.tes.higo.higotes.base.BaseFragment
import com.tes.higo.higotes.model.state.DifficultState
import com.tes.higo.higotes.databinding.FragmentDifficultBinding
import com.tes.higo.higotes.ui.quiz.QuizFragment
import com.tes.higo.higotes.util.IOnBackPressed

class DifficultFragment : BaseFragment<FragmentDifficultBinding>() {

    override fun layoutResId() = R.layout.fragment_difficult

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar((activity as AppCompatActivity).findViewById(R.id.toolbar))

        (activity as AppCompatActivity).supportActionBar?.title = "Level"

        initAction()
    }

    private fun initAction() {
        val fragment = QuizFragment()
        val bundle = Bundle()
        var difficult = "difficult"

        binding.btnEasy.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                bundle.putString(difficult, DifficultState.EASY.toString().toLowerCase())
                fragment.arguments = bundle
                moveToAnotherFragment(R.id.container_frame, fragment)
            }
        })

        binding.btnMedium.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                bundle.putString(difficult, DifficultState.MEDIUM.toString().toLowerCase())
                fragment.arguments = bundle
                moveToAnotherFragment(R.id.container_frame, fragment)
            }
        })

        binding.btnHard.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                bundle.putString(difficult, DifficultState.HARD.toString().toLowerCase())
                fragment.arguments = bundle
                moveToAnotherFragment(R.id.container_frame, fragment)
            }
        })
    }

}