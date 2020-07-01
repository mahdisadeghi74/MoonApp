package com.moon.ui.auth.fragments

import android.content.ContentValues.TAG
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
import com.dependencies.buria.android.support.daggerktx2.viewmodel.ViewModelProvidersFactory
import com.moon.R
import com.moon.ui.Response
import com.moon.ui.auth.AccountViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_sign_up.*
import javax.inject.Inject

class SignUpFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvidersFactory

    lateinit var accountViewModel: AccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        accountViewModel =
            ViewModelProviders.of(this, viewModelProvidersFactory).get(AccountViewModel::class.java)

        observe()
        // animation
        var animation: Animation = AnimationUtils.loadAnimation(context, R.anim.animres)
        tilConfirmPassword.animation = animation
        tilUserName.animation = animation
        btnSignUp.animation = animation
        tvSignIn.animation = animation

        tvSignIn.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        etUserName.doAfterTextChanged {
            btnSignUp.isEnabled = checkIsEmpty()
        }
        etPassWord.doAfterTextChanged {
            btnSignUp.isEnabled = checkIsEmpty()
        }
        etConfirmPassword.doAfterTextChanged {
            btnSignUp.isEnabled = checkIsEmpty()
        }
        etEmail.doAfterTextChanged {
            btnSignUp.isEnabled = checkIsEmpty()
        }
        btnSignUp.setOnClickListener {
            accountViewModel.register(
                email = etEmail.text.toString(),
                password = etPassWord.text.toString(),
                password2 = etConfirmPassword.text.toString(),
                userName = etUserName.text.toString()
            )
        }


    }

    private fun checkIsEmpty(): Boolean {
        return etEmail.text.toString().isNotEmpty() &&
                etUserName.text.toString().isNotEmpty() &&
                etPassWord.text.toString().isNotEmpty() &&
                etConfirmPassword.text.toString().isNotEmpty() &&
                etPassWord.text.toString() == etConfirmPassword.text.toString()
    }

    private fun observe() {
        accountViewModel.observe().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Response.Status.SUCCESS ->
                    requireActivity().onBackPressed()
                Response.Status.LOADING ->
                    Log.d(TAG, "registration: LOADING ${it.message}")
                Response.Status.ERROR ->
                    Log.d(TAG, "registration: ERROR ${it.message}")
            }
        })
    }
}