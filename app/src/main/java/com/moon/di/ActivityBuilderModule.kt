package com.moon.di

import com.moon.di.auth.AuthModule
import com.moon.di.auth.AuthScope
import com.moon.di.auth.AuthViewModelModule
import com.moon.di.auth.AuthFragmentBuilderModule
import com.moon.di.story.StoryFragmentBuilderModule
import com.moon.ui.story.activity.MainActivity
import com.moon.di.story.StoryModule
import com.moon.di.story.StoryScope
import com.moon.di.story.StoryViewModelModule
import com.moon.ui.auth.AuthActivity
import com.moon.ui.label.LabelViewModel
import com.moon.ui.story.activity.AddStoryActivity
import com.moon.ui.story.activity.StoryActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
@Module
abstract class ActivityBuilderModule {

    @StoryScope
    @ContributesAndroidInjector(modules = [StoryModule::class, StoryViewModelModule::class, AuthModule::class, AuthViewModelModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @AuthScope
    @ContributesAndroidInjector(modules = [AuthModule::class, AuthViewModelModule::class, AuthFragmentBuilderModule::class])
    abstract fun contributeAuthActivity(): AuthActivity

    @StoryScope
    @ContributesAndroidInjector(modules = [StoryModule::class, StoryViewModelModule::class, StoryFragmentBuilderModule::class])
    abstract fun contributeStoryActivity(): StoryActivity

    @StoryScope
    @ContributesAndroidInjector(modules = [StoryModule::class, StoryViewModelModule::class, StoryFragmentBuilderModule::class])
    abstract fun contributeAddStoryActivity(): AddStoryActivity
}