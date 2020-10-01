package com.tes.higo.higotes.ui.ranking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tes.higo.higotes.R
import com.tes.higo.higotes.model.db.User
import kotlinx.android.synthetic.main.item_ranking.view.*

class LevelRankingRecyclerViewAdapter(val users : MutableList<User>) : RecyclerView.Adapter<LevelRankingRecyclerViewAdapter.LevelRankingViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = LevelRankingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_ranking, parent, false))

    override fun onBindViewHolder(holder: LevelRankingViewHolder, position: Int) {
        holder.bind(users.get(position), position)
    }

    override fun getItemCount() = users.size

    class LevelRankingViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        fun bind(user : User, position: Int) {
            itemView.tv_nomor.text = "#" + (position + 1).toString()
            itemView.tv_username.text = user.username
            itemView.tv_score.text = user.score.toString()
        }
    }
}