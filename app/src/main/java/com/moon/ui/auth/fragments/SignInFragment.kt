package com.moon.ui.auth.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.dependencies.buria.android.support.daggerktx2.viewmodel.ViewModelProvidersFactory
import com.moon.R
import com.moon.ui.auth.AuthResponse
import com.moon.ui.auth.AuthViewModel
import com.moon.ui.story.activity.StoryActivity
import com.moon.util.Constants
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_in.etEmail
import kotlinx.android.synthetic.main.fragment_sign_in.etPassWord
import javax.inject.Inject

class SignInFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvidersFactory

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    lateinit var authViewModel: AuthViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel = ViewModelProviders.of(this, viewModelProvidersFactory).get(AuthViewModel::class.java)
        observe()

        tvSignUp.setOnClickListener {
            view.findNavController().navigate(R.id.acSignInToSignUp)
        }
        // animation
        var animation: Animation = AnimationUtils.loadAnimation(context, R.anim.animres)
        tilEmail.animation = animation
        tilPassword.animation = animation
        btnSignIn.animation = animation
        tvSignUp.animation = animation
        tvForgotPassword.animation = animation

        btnSignIn.setOnClickListener{
            authViewModel.login(etEmail.text.toString(), etPassWord.text.toString())
        }
        etEmail.doAfterTextChanged {
            if (it.toString().isNotEmpty()) {
                btnSignIn.isEnabled = etPassWord.text.toString().isNotEmpty()
            }else btnSignIn.isEnabled = false
        }
        etPassWord.doAfterTextChanged {
            if (it.toString().isNotEmpty()) {
                btnSignIn.isEnabled = etEmail.text.toString().isNotEmpty()
            }else btnSignIn.isEnabled = false
        }


    }

    private fun observe() {
        authViewModel.observe().observe(viewLifecycleOwner, Observer {
            when(it?.authStatus){
                AuthResponse.AuthStatus.AUTHENTICATED ->{
                    Log.d("MY_TAG", "onCreate: AUTHENTICATED")
                    startActivity(Intent(context, StoryActivity::class.java))
                    sharedPreferences.edit().putString(Constants.TOKEN_KEY, it.data?.token).apply()
                    requireActivity().finish()
                }

                AuthResponse.AuthStatus.LOADING ->{
                    Log.d("MY_TAG", "onCreate: LOADING")
                }

                AuthResponse.AuthStatus.NOT_AUTHENTICATED ->{
                    Log.d("MY_TAG", "onCreate: NOT_AUTHENTICATED")
                    sharedPreferences.edit().remove(Constants.TOKEN_KEY).apply()
                    etEmail.error = "error"
                }

                AuthResponse.AuthStatus.ERROR ->{
                    Log.d("MY_TAG", "onCreate: ERROR: ${it.message}")
                    etEmail.error = "error"
                }
            }
        })

    }
}