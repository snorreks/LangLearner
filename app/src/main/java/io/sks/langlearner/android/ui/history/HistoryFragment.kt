package io.sks.langlearner.android.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.sks.langlearner.android.R
import io.sks.langlearner.android.model.History
import kotlinx.android.synthetic.main.fragment_history.*


class HistoryFragment : Fragment() {


    private val histories = arrayListOf<History>()
    private val historyAdapter =
        HistoryAdapter(histories)
    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initViewModel()
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViewModel() {
        historyViewModel =
        ViewModelProviders.of(this).get(HistoryViewModel::class.java)
        // Observe reminders from the view model, update the list when the data is changed.
        historyViewModel.histories.observe(this, Observer { histories ->
            this@HistoryFragment.histories.clear()
            this@HistoryFragment.histories.addAll(histories)
            historyAdapter.notifyDataSetChanged()
        })
    }

    private fun initViews() {
        // Initialize the recycler view with a linear layout manager, adapter, decorator and swipe callback.
        rvHistories.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvHistories.adapter = historyAdapter
        rvHistories.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        createItemTouchHelper().attachToRecyclerView(rvHistories)
    }

    private fun createItemTouchHelper(): ItemTouchHelper {
        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }
            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val historyToDelete = histories[position]
                historyViewModel.deleteHistory(historyToDelete)
            }
        }
        return ItemTouchHelper(callback)
    }
}