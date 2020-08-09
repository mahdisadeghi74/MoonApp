package com.moon.ui.story.fragment

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dependencies.buria.android.support.daggerktx2.viewmodel.ViewModelProvidersFactory
import com.moon.R
import com.moon.model.Story
import com.moon.ui.Response
import com.moon.ui.StoryViewModel
import com.moon.ui.story.adapter.StoriesAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_search_story.*
import javax.inject.Inject

class SearchStoryFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvidersFactory

    @Inject
    lateinit var storiesAdapter: StoriesAdapter

    lateinit var storyViewModel: StoryViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        storyViewModel = ViewModelProviders.of(this, viewModelProvidersFactory).get(StoryViewModel::class.java)

        imbBack.setOnClickListener { 
            view.findNavController().navigateUp()
        }
        swStory.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG, "onQueryTextSubmit: $query")
                query?.let {
                    if (it.isNotEmpty() && it.isNotBlank()){
                        storyViewModel.getStories(Story(), search = query)
                        observe()
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return  false
            }
        })
    }

    private fun setAdapter() {
        rvStories.layoutManager = LinearLayoutManager(context)

        storiesAdapter.onClickListenerItem = {story, _, _ ->
            view?.findNavController()?.navigate(SearchStoryFragmentDirections.actionSearchStoryFragmentToShowStoryFragment(story))
        }
    }

    private fun observe() {
        storyViewModel.observe().observe(viewLifecycleOwner, Observer {
            if (it.status == Response.Status.SUCCESS) {
                rvStories.adapter = storiesAdapter
                storiesAdapter.setStories(stories = it.data)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_story, container, false)
    }
}