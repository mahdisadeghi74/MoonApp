package com.moon.di.auth

import com.moon.di.auth.AuthModule
import com.moon.di.auth.AuthScope
import com.moon.di.auth.AuthViewModelModule
import com.moon.ui.auth.fragments.SignInFragment
import com.moon.ui.auth.fragments.SignUpFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AuthFragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributesSignInFragment(): SignInFragment

    @ContributesAndroidInjector()
    abstract fun contributesSignUpFragment(): SignUpFragment
}