package com.moon.ui.story.fragment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.moon.R
import com.moon.model.Story
import com.moon.util.getNavigationResult
import com.moon.util.getNavigationResultLiveData
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_show_story.*

class ShowStoryFragment : DaggerFragment() {

    val args: ShowStoryFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_show_story, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(android.R.transition.move)
        sharedElementReturnTransition = TransitionInflater.from(context)
            .inflateTransition(android.R.transition.move)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var story = args.story

        tvStoryAuthor.text = story?.created_by
        tvStoryDate.text = story?.publish
        tvStoryContent.text = story?.content
        tvStoryTitle.text = story?.title

        var iv: ImageView

        val result = getNavigationResultLiveData<Story>(AddBranchFragment.ADD_BRANCH_RESULT)
        result?.observe(viewLifecycleOwner, Observer {
            story = args.story
            tvStoryAuthor.text = story?.created_by
            tvStoryDate.text = story?.publish
            tvStoryContent.text = story?.content
            tvStoryTitle.text = story?.title + "asb"

            Log.d(TAG, "onViewCreatedBranch: ${story?.branch}")
        })
    }
}