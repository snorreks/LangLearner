package io.sks.langlearner.android.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.sks.langlearner.android.R
import io.sks.langlearner.android.model.LangCard
import io.sks.langlearner.android.services.FirebaseAuthService


class LangCardAdapter(
    private val langCards: List<LangCard>,
    private val insertToHistory: (langCard: LangCard, resultText: String) -> Unit,
    private val updateAchievment: (langCard: LangCard) -> Unit
) : RecyclerView.Adapter<LangCardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.lang_card_view, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(langCards[position])
    }


    override fun getItemCount(): Int {
        return langCards.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvNativeText: TextView = view.findViewById(R.id.tvNativeText)
        private val etResultText: EditText = view.findViewById(R.id.etResultText)
        private val btnSubmit: Button = view.findViewById(R.id.btnSubmit)
        private val btnGiveUp: Button = view.findViewById(R.id.btnGiveUp)
        private val ivThumbnail: ImageView = view.findViewById(R.id.ivThumbnail)

        fun bind(langCard: LangCard) {
            val nativeText = FirebaseAuthService.currentUser!!.getNativeText(langCard.text)
            val selectedText = FirebaseAuthService.currentUser!!.getSelectedText(langCard.text)
            val resultText = etResultText.text.toString()
            //            itemView
            tvNativeText.text = nativeText
            Glide.with(ivThumbnail)
                .load(langCard.thumbnailUrl)
                .into(ivThumbnail)
            btnSubmit.setOnClickListener { v ->
                if (selectedText == resultText) {
                    Toast.makeText(v.context, R.string.correct, Toast.LENGTH_SHORT).show()
                    updateAchievment(langCard)
                } else {
                    Toast.makeText(
                        v.context, v.context.getString(R.string.wrong, selectedText)
                        , Toast.LENGTH_SHORT
                    ).show()
                }
                insertToHistory(langCard, resultText)
            }
            btnGiveUp.setOnClickListener { v ->
                Toast.makeText(v.context, selectedText, Toast.LENGTH_SHORT).show()
            }
        }
    }
}