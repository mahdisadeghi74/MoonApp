package com.moon.ui.story.fragment

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.dependencies.buria.android.support.daggerktx2.viewmodel.ViewModelProvidersFactory
import com.moon.R
import com.moon.model.Label
import com.moon.model.Story
import com.moon.ui.StoryViewModel
import com.moon.ui.label.LabelViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_select_label.*
import javax.inject.Inject

class SelectLabelFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvidersFactory

    lateinit var labelViewMode: LabelViewModel
    lateinit var storyViewModel: StoryViewModel

    val args: SelectLabelFragmentArgs by navArgs()
    var story: Story? = null
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("TAG", "observe:label 4")
        labelViewMode = ViewModelProviders.of(this, viewModelProvidersFactory).get(LabelViewModel::class.java)
        storyViewModel = ViewModelProviders.of(this, viewModelProvidersFactory).get(StoryViewModel::class.java)

        story = args.Story
        labelViewMode.getLabels()
        observe()

    }

    private fun observeAddStory() {
        storyViewModel.observeAddStory().observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "observeAddStory: ${it.status} : ${it.data?.id}")
            requireActivity().finish()
        })
    }
    private fun observe() {
        labelViewMode.observe().observe(viewLifecycleOwner, Observer {
            Log.d("TAG", "observe:label ${it.data}")
            var label = it.data
            val arrayAdapter: ArrayAdapter<Label> = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line,
                label ?: arrayListOf())
           actLabel.setAdapter(arrayAdapter)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imbNext.setOnClickListener {
            storyViewModel.createStory(story = story ?: Story())
            observeAddStory()
        }

        imbBack.setOnClickListener {
            requireActivity().onBackPressed()
        }


    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_select_label, container, false)
    }
}