package io.sks.langlearner.android.ui.achievements

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.sks.langlearner.android.R
import io.sks.langlearner.android.model.Achievement


class AchievementsAdapter(
    private val achievements: List<Achievement>
) : RecyclerView.Adapter<AchievementsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.achievement_card, parent, false))
    }

    override fun getItemCount(): Int {
        return achievements.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(achievements[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        private val tvDescription: TextView = view.findViewById(R.id.tvDescription)
        private val ivThumbnail: ImageView = view.findViewById(R.id.ivThumbnail)

        fun bind(achievement: Achievement) {
            Log.d("aa", achievement.title)
            Glide.with(ivThumbnail)
                .load(achievement.thumbnailUrl)
                .into(ivThumbnail)
            tvTitle.text = achievement.title
            tvDescription.text = achievement.description
        }

    }
}

