package com.moon.ui.story.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dependencies.buria.android.support.daggerktx2.viewmodel.ViewModelProvidersFactory
import com.moon.R
import com.moon.model.Story
import com.moon.ui.Response
import com.moon.ui.StoryViewModel
import com.moon.util.hide
import com.moon.util.show
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_edit_story.*
import javax.inject.Inject

class EditStoryActivity : DaggerAppCompatActivity() {

    var story: Story? = null

    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvidersFactory

    lateinit var storyVieModel: StoryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_story)

        story = intent.getParcelableExtra("story")
        storyVieModel = ViewModelProviders.of(this, viewModelProvidersFactory).get(StoryViewModel::class.java)
        observe()
        tvStoryTitle.text = story?.title
        etStoryContent.setText(story?.content)

        imbBack.setOnClickListener {
            onBackPressed()
        }

        imbDoneEdit.setOnClickListener {
            if (story != null) {
                story?.content = etStoryContent.text.toString()
                storyVieModel.mergeRequestStory(story!!)
            }
        }

        etStoryContent.doAfterTextChanged {
            if (it?.toString()?.isNotEmpty() == true){
                imbDoneEdit.show()
            }else{
                imbDoneEdit.hide()
            }
        }
    }

    private fun observe() {
        storyVieModel.observeAddStory().observe(this, Observer {
            if (it.status == Response.Status.SUCCESS) {
                Toast.makeText(this, getString(R.string.merge_request_sent_success), Toast.LENGTH_LONG)
                    .show()
                onBackPressed()
            }else {
                Toast.makeText(this, getString(R.string.merge_request_sent_error), Toast.LENGTH_LONG)
                    .show()
            }
        })
    }
}