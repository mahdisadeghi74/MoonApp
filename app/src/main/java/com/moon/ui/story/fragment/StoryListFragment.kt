package com.moon.ui.story.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import com.dependencies.buria.android.support.daggerktx2.viewmodel.ViewModelProvidersFactory
import com.moon.R
import com.moon.model.Story
import com.moon.ui.StoryViewModel
import com.moon.ui.story.adapter.StoriesAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_story_list.*
import kotlinx.android.synthetic.main.fragment_story_list.view.*
import kotlinx.android.synthetic.main.fragment_story_list.view.rvStories
import javax.inject.Inject

class StoryListFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvidersFactory

    @Inject
    lateinit var storiesAdapter: StoriesAdapter

    lateinit var storyViewModel: StoryViewModel

    var onClickListenerItem: ((Story) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_story_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setRecyclerView()
        storyViewModel =
            ViewModelProviders.of(this, viewModelProvidersFactory).get(StoryViewModel::class.java)

        storyViewModel.getStories(Story())

        storiesAdapter.onClickListenerItem = { story , position, itemView->
            onClickListenerItem?.let { function ->  function(story) }
            val extra = FragmentNavigatorExtras(
                itemView to "storyView"
            )
            view?.findNavController()?.navigate(StoryListFragmentDirections.asShowListToShowStoryFragment(story),
                extra
            )
        }
        observe()
    }
    private fun setRecyclerView() {
        view?.rvStories?.layoutManager = LinearLayoutManager(context)
        view?.rvStories?.adapter = storiesAdapter
    }

    private fun observe() {
        storyViewModel.observe().observe(requireActivity(), Observer {
            it.data?.let { data -> storiesAdapter.setStories(data) }
        })
    }

    override fun onDestroyView() {
        rvStories.adapter = null
        super.onDestroyView()
    }
}