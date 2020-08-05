package com.moon.ui.story.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.moon.R
import com.moon.model.Story
import com.moon.ui.story.fragment.MergeRequestsFragmentDirections
import com.moon.ui.story.fragment.SubmitMergeRequestFragmentArgs
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_merge_requests.*

class MergeRequestsActivity : DaggerAppCompatActivity() {
    var story: Story? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_merge_requests)

        story = intent.getParcelableExtra("story")
        if (story != null) {
            frgMergeRequests.findNavController().setGraph(
                R.navigation.merge_request_nav_graph,
                bundleOf("story" to story)
            )
        }
    }
}