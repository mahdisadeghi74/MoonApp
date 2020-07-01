package com.moon

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dependencies.buria.android.support.daggerktx2.viewmodel.ViewModelProvidersFactory
import com.moon.ui.auth.AuthActivity
import com.moon.ui.auth.AuthResponse
import com.moon.ui.auth.AuthViewModel
import com.moon.ui.story.activity.StoryActivity
import com.moon.util.Constants
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


abstract class BaseActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvidersFactory

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private var authViewModel: AuthViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val token = sharedPreferences.getString(Constants.TOKEN_KEY, "")
        if (token?.isNotEmpty() == true){
            startActivity(Intent(applicationContext, StoryActivity::class.java))
            finish()
        } else {
            startActivity(Intent(applicationContext, AuthActivity::class.java))
            finish()
        }
    }
}