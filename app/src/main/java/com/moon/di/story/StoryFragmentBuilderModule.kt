package com.moon.di.story

import com.moon.ui.story.fragment.*
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

    @ContributesAndroidInjector
    abstract fun contributeMergeRequestsFragment(): MergeRequestsFragment

    @ContributesAndroidInjector
    abstract fun contributeSubmitMergeRequestFragment(): SubmitMergeRequestFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchStoryFragment(): SearchStoryFragment
}