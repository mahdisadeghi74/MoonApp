package com.moon.di.story

import android.app.Application
import android.content.Context
import com.moon.model.LikeStory
import com.moon.network.auth.RegisterApi
import com.moon.network.label.GetLabelsApi
import com.moon.network.story.*
import com.moon.ui.story.activity.StoryActivity
import com.moon.ui.story.adapter.JoinRequestAdapter
import com.moon.ui.story.adapter.StoriesAdapter
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class StoryModule {

    @Provides
    @StoryScope
    fun ss(): String{
        return "tetete"
    }

    @Provides
    @StoryScope
    fun provideStoriesAdapter(): StoriesAdapter{
        return StoriesAdapter()
    }

    @Provides
    @StoryScope
    fun provideSJoinRequestAdapter(): JoinRequestAdapter{
        return JoinRequestAdapter()
    }

    @Provides
    @StoryScope
    fun providerGetStoryApi(retrofit: Retrofit): GetStoriesApi {
        return retrofit.create(GetStoriesApi::class.java)
    }

    @Provides
    @StoryScope
    fun providerCreateStoryApi(retrofit: Retrofit): CreateStoryApi {
        return retrofit.create(CreateStoryApi::class.java)
    }
    @Provides
    @StoryScope
    fun provideLikeStoryApi(retrofit: Retrofit): ClapStoryApi{
        return retrofit.create(ClapStoryApi::class.java)
    }

    @Provides
    @StoryScope
    fun provideLabelApi(retrofit: Retrofit): GetLabelsApi{
        return retrofit.create(GetLabelsApi::class.java)
    }
    @Provides
    @StoryScope
    fun provideCreateStoryBranchApiApi(retrofit: Retrofit): CreateStoryBranchApi{
        return retrofit.create(CreateStoryBranchApi::class.java)
    }

    @Provides
    @StoryScope
    fun provideCreateJoinStoryApi(retrofit: Retrofit): JoinStoryApi{
        return retrofit.create(JoinStoryApi::class.java)
    }


    @Provides
    @StoryScope
    fun provideGetAllJoinRequests(retrofit: Retrofit): GetAllJoinRequests{
        return retrofit.create(GetAllJoinRequests::class.java)
    }

    @Provides
    @StoryScope
    fun provideChangeJoinStoryStatusApi(retrofit: Retrofit): ChangeJoinStoryStatusApi{
        return retrofit.create(ChangeJoinStoryStatusApi::class.java)
    }

    @Provides
    @StoryScope
    fun provideMergeRequestStoryApi(retrofit: Retrofit): MergeRequestStoryApi{
        return retrofit.create(MergeRequestStoryApi::class.java)
    }

    @Provides
    @StoryScope
    fun provideComparisonTwoStoriesApi(retrofit: Retrofit): ComparisonTwoStoriesApi{
        return retrofit.create(ComparisonTwoStoriesApi::class.java)
    }

    @Provides
    @StoryScope
    fun provideSubmitMergeRequestStoryApi(retrofit: Retrofit): SubmitMergeRequestStoryApi{
        return retrofit.create(SubmitMergeRequestStoryApi::class.java)
    }
}