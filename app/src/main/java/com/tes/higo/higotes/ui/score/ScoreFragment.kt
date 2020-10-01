package com.tes.higo.higotes.ui.score

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.tes.higo.higotes.R
import com.tes.higo.higotes.databinding.FragmentScoreBinding
import com.tes.higo.higotes.di.binding.BindingFragment
import com.tes.higo.higotes.model.db.User
import com.tes.higo.higotes.model.state.Answer
import com.tes.higo.higotes.ui.home.HomeFragment
import com.tes.higo.higotes.util.IOnBackPressed
import kotlinx.android.synthetic.main.dialog_add_user.view.*


class ScoreFragment : BindingFragment<FragmentScoreBinding, ScoreViewModel>(), IOnBackPressed {

    private var answer: Answer? = null
    
    override fun layoutResId() = R.layout.fragment_score

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createViedModel(ScoreViewModel::class.java)

        (activity as AppCompatActivity).setSupportActionBar((activity as AppCompatActivity).findViewById(R.id.toolbar))
        (activity as AppCompatActivity).supportActionBar?.title = "Score"

        if(arguments != null) {
            answer = (arguments?.getSerializable("answer") as Answer)
        }

        initView()
        observableViewModel()
    }

    private fun initView() {
        initChart()
        binding.tvScore.text = answer?.score.toString()
        binding.tvFalse.text = answer?.falseAnswerCount.toString()
        binding.tvSkip.text = answer?.skipAnswerCount.toString()

        binding.btnBackHome.setOnClickListener {
            moveToAnotherFragment(R.id.container_frame, HomeFragment())
        }

        binding.btnSaveUser.setOnClickListener {
            showAddUserDialog()
        }
    }

    private fun initChart() {
        val pieEntries : MutableList<PieEntry> = mutableListOf()
        pieEntries.add(PieEntry(answer?.trueAnswerCount!!.toFloat(), 1))
        pieEntries.add(PieEntry(answer?.falseAnswerCount!!.toFloat(), 2))
        pieEntries.add(PieEntry(answer?.skipAnswerCount!!.toFloat(), 3))

        val pieDataset = PieDataSet(pieEntries, "Answer")

        val piecolors = intArrayOf(
            Color.rgb(52, 235, 70),
            Color.rgb(207, 21, 21),
            Color.rgb(235, 255, 59)
        )

        pieDataset.colors = ColorTemplate.createColors(piecolors)

        val legends : MutableList<LegendEntry> = mutableListOf()
        val labels : MutableList<String> = mutableListOf()
        labels.add("True")
        labels.add("False")
        labels.add("Skip")

        for (i in 0 until labels.size) {
            val entry = LegendEntry()
            entry.label = labels.get(i)
            entry.formColor = piecolors.get(i)
            legends.add(entry)
        }

        val description = Description()
        description.text = "Answer"

        binding.piechart.data = PieData(pieDataset)
        binding.piechart.legend.isEnabled = true
        binding.piechart.legend.setCustom(legends)
        binding.piechart.description = description
    }

    private fun showAddUserDialog() {
        val dialog = AlertDialog.Builder(context!!)
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_user, null)
        dialog.setView(dialogView)
        dialog.setCancelable(false)

        val show = dialog.show()
        dialogView.tv_cancel.setOnClickListener {
            show.cancel()
        }

        dialogView.tv_save.setOnClickListener {
            val username = dialogView.et_username.text.toString()
            if(username == "") {
                onMessage("Username Harus Diisi")
            } else {
               show.cancel()
               viewModel.insertUser(User(username = username, difficult = answer?.difficult!!, score = answer?.score!!))
            }
        }
    }

    override fun observableViewModel() {
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if(it!!){
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        })

        viewModel.success.observe(viewLifecycleOwner, Observer {
            if(it!!) {
                onMessage("Success menambahkan user")
                moveToAnotherFragment(R.id.container_frame, HomeFragment())
            } else {
                onMessage("Gagal menambahkan user")
            }
        })
    }

    override fun onBackPressed(): Boolean {
        moveToAnotherFragment(R.id.container_frame, HomeFragment())
        return true
    }
}