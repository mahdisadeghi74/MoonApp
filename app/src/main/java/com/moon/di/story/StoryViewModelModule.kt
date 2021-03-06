package com.moon.di.story

import androidx.lifecycle.ViewModel
import com.moon.di.ViewModelKey
import com.moon.model.JoinStory
import com.moon.ui.StoryViewModel
import com.moon.ui.comparative.ComparativeViewModel
import com.moon.ui.joinStory.JoinStoryViewModel
import com.moon.ui.label.LabelViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class StoryViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(StoryViewModel::class)
    abstract fun bindStoryViewModel(storyViewModel: StoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LabelViewModel::class)
    abstract fun bindLabelViewModel(labelViewModel: LabelViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(JoinStoryViewModel::class)
    abstract fun bindJoinStoryViewModel(joinStoryViewModel: JoinStoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ComparativeViewModel::class)
    abstract fun bindComparativeViewModel(joinStoryViewModel: ComparativeViewModel): ViewModel
}