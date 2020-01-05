package io.sks.langlearner.android.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.sks.langlearner.android.R
import io.sks.langlearner.android.model.History


class HistoryAdapter(private val histories: List<History>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.history_card, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return histories.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(histories[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvResult: TextView = itemView.findViewById(R.id.tvResult)
        private val tvSelectedText: TextView = itemView.findViewById(R.id.tvSelectedText)
        private val tvNativeText: TextView = itemView.findViewById(R.id.tvNativeText)
        private val tvDate: TextView = itemView.findViewById(R.id.tvDate)

        fun bind(history: History) {
            tvResult.text = history.resultText
            tvSelectedText.text = history.selectedText
            tvNativeText.text = history.nativeText
            tvDate.text = history.createdAt.toString()
        }
    }
}

