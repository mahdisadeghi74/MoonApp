package com.moon.ui.story.activity

import android.os.Bundle
import com.moon.BaseActivity
import com.moon.R
import com.moon.model.Account
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}