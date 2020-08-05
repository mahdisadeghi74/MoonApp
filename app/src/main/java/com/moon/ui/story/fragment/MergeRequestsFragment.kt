package com.moon.ui.story.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.dependencies.buria.android.support.daggerktx2.viewmodel.ViewModelProvidersFactory
import com.moon.R
import com.moon.model.Story
import com.moon.ui.Response
import com.moon.ui.StoryViewModel
import com.moon.ui.story.adapter.StoriesAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_merge_request_fragment.*
import javax.inject.Inject

class MergeRequestsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvidersFactory

    @Inject
    lateinit var storiesAdapter: StoriesAdapter

    lateinit var storyViewModel: StoryViewModel
    val args: MergeRequestsFragmentArgs by navArgs()
    var selectedStory: Story ?= null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_merge_request_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectedStory = args.story
        storyViewModel = ViewModelProviders.of(this, viewModelProvidersFactory).get(StoryViewModel::class.java)
        setRecyclerViewAdapter()
        observe()

        if (selectedStory != null) {
            selectedStory?.title = null
            storyViewModel.getStories(story = selectedStory!!)
        }
        else Toast.makeText(context, "error da", Toast.LENGTH_SHORT).show()

    }

    private fun setRecyclerViewAdapter() {
        rvMergeRequests.layoutManager = LinearLayoutManager(context)
        rvMergeRequests.adapter = storiesAdapter

        storiesAdapter.onClickListenerItem = {story,position,_ ->
            view?.findNavController()?.navigate(MergeRequestsFragmentDirections
                .actionMergeRequestsFragmentToSubmitMergeRequestFragment(story=story))
        }
    }

    private fun observe() {
        storyViewModel.observe().observe(viewLifecycleOwner, Observer {
            if (it.status == Response.Status.SUCCESS)
                storiesAdapter.setStories(it.data)
        })
    }


}