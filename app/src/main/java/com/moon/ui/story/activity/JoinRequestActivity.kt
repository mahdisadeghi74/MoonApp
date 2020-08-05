package com.moon.ui.story.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.dependencies.buria.android.support.daggerktx2.viewmodel.ViewModelProvidersFactory
import com.moon.R
import com.moon.model.JoinStory
import com.moon.model.Story
import com.moon.ui.joinStory.JoinStoryViewModel
import com.moon.ui.story.adapter.JoinRequestAdapter
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_join_request.*
import javax.inject.Inject

class JoinRequestActivity : DaggerAppCompatActivity(){

    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvidersFactory

    @Inject
    lateinit var joinRequestAdapter: JoinRequestAdapter

    lateinit var joinStoryViewModel: JoinStoryViewModel
    var story: Story? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_request)
        story = intent.getParcelableExtra("story")

        joinStoryViewModel = ViewModelProviders.of(this, viewModelProvidersFactory).get(JoinStoryViewModel::class.java)
        setAdapter()
        observe()
        Log.d("TAG", "getAllJoinRequestsStoryId: ${story!!.id}")
        joinStoryViewModel.getAllJoinRequests(storyId = story?.id ?: "")
    }

    private fun setAdapter() {
        rvJoinRequests.layoutManager = LinearLayoutManager(applicationContext)
        rvJoinRequests.adapter = joinRequestAdapter
        joinRequestAdapter.changeStatusListener = { joinStory, position ->
            Log.d("TAG", "setAdapter: ${story!!.id}")
            joinStoryViewModel.changeJoinStoryStatus(joinStory, storyId = story?.id ?: "")
        }
    }

    private fun observe() {
        joinStoryViewModel.observeRequests().observe(this, Observer {
            joinRequestAdapter.addJoinRequest(it.data)
        })

        joinStoryViewModel.observeChangeStatus().observe(this, Observer {
            joinRequestAdapter.updateJoinRequest(it.data)
        })
    }

}