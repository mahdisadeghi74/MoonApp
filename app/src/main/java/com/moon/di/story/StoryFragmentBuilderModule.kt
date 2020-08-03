package com.moon.di.story

import com.moon.ui.story.fragment.AddBranchFragment
import com.moon.ui.story.fragment.SelectLabelFragment
import com.moon.ui.story.fragment.ShowStoryFragment
import com.moon.ui.story.fragment.StoryListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class StoryFragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeStoryListFragment(): StoryListFragment

    @ContributesAndroidInjector
    abstract fun contributeShowStoryFragment(): ShowStoryFragment

    @ContributesAndroidInjector
    abstract fun contributeSelectLabelFragment(): SelectLabelFragment

    @ContributesAndroidInjector
    abstract fun contributeAddBranchFragment(): AddBranchFragment
}