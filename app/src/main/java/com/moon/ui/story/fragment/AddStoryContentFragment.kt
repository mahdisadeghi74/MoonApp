package com.moon.ui.story.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.moon.R
import kotlinx.android.synthetic.main.fragment_add_story_content.view.*

class AddStoryContentFragment : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.etStoryTitle.setOnClickListener {
            view.findNavController().navigate(AddStoryContentFragmentDirections.actionAddStoryContentFragmentToSelectLabelFragment())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_story_content, container, false)
    }
}