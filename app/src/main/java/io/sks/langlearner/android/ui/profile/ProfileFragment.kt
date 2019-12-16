package io.sks.langlearner.android.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import io.sks.langlearner.android.MainActivity
import io.sks.langlearner.android.MainEmptyActivity
import io.sks.langlearner.android.R
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initViewModel()
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }


    private fun initViews() {
        btnSignOut.setOnClickListener {
            profileViewModel.signOut()
            val intent = Intent(activity, MainEmptyActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initViewModel() {
        profileViewModel =
            ViewModelProviders.of(this).get(ProfileViewModel::class.java)
    }


}