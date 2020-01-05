package io.sks.langlearner.android.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.sks.langlearner.android.R
import io.sks.langlearner.android.model.LangCard
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private val langCards = arrayListOf<LangCard>()
    private val langCardsAdapter =
        LangCardAdapter(
            langCards,
            { langCard, resultText -> homeViewModel.submit(langCard, resultText) })
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        initViewModel()
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews(){
        rvLangCards.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvLangCards.adapter = langCardsAdapter
        rvLangCards.addItemDecoration(DividerItemDecoration(context, 0))
    }


    private fun initViewModel() {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        // Observe reminders from the view model, update the list when the data is changed.
        homeViewModel.langCards.observe(this, Observer { histories ->
            this@HomeFragment.langCards.clear()
            this@HomeFragment.langCards.addAll(histories)
            langCardsAdapter.notifyDataSetChanged()
        })
    }
}
