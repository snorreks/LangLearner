package io.sks.langlearner.android.ui.achievements

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
import io.sks.langlearner.android.model.Achievement
import kotlinx.android.synthetic.main.fragment_achievements.*

class AchievementsFragment : Fragment() {


    private val achievements = arrayListOf<Achievement>()
    private val achievementsAdapter =
        AchievementsAdapter(achievements)
    private lateinit var achievementsViewModel: AchievementsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        initViewModel()
        return inflater.inflate(R.layout.fragment_achievements, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViewModel() {
        achievementsViewModel =
            ViewModelProviders.of(this).get(AchievementsViewModel::class.java)

        // Observe reminders from the view model, update the list when the data is changed.
        achievementsViewModel.achievements.observe(this, Observer { achievements ->
            this@AchievementsFragment.achievements.clear()
            this@AchievementsFragment.achievements.addAll(achievements)
            achievementsAdapter.notifyDataSetChanged()
        })
    }

    private fun initViews() {
        // Initialize the recycler view with a linear layout manager, adapter, decorator and swipe callback.
        rvAchievements.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvAchievements.adapter = achievementsAdapter
        rvAchievements.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }







}