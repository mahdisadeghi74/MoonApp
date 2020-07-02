package com.moon.ui.story.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.moon.R
import com.moon.model.Story
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_add_story.*

class AddStoryActivity : DaggerAppCompatActivity() {
    var story: Story? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_story)

    }
}