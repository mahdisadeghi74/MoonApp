package com.moon.ui.comparative

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.*
import com.moon.model.Comparative
import com.moon.model.Story
import com.moon.model.Token
import com.moon.network.story.ComparisonTwoStoriesApi
import com.moon.network.story.SubmitMergeRequestStoryApi
import com.moon.ui.Response
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ComparativeViewModel @Inject constructor(private var comparisonTwoStoriesApi: ComparisonTwoStoriesApi,
                                               private var submitMergeRequestStoryApi: SubmitMergeRequestStoryApi): ViewModel() {
    var mediatorLiveData = MediatorLiveData<Response<Comparative>>()
    var mediatorSubmitStory = MediatorLiveData<Response<Story>>()

    @Inject
    lateinit var token: Token

    fun comparisionStories(story: Story){
        var source = LiveDataReactiveStreams.fromPublisher<Response<Comparative>>(
            comparisonTwoStoriesApi.comparison(storyId = story.id, token = token.token)
                .onErrorReturn {
                    Log.d(TAG, "comparisionStories: error: $it")
                    Comparative()
                }
                .map(Function<Comparative, Response<Comparative>>{
                    Log.d(TAG, "comparisionStories: ${it.storyContentNew}")
                    Response.success(data = it)
                })
                .subscribeOn(Schedulers.io())
        )

        mediatorLiveData.addSource(source, Observer {
            mediatorLiveData.value = it
            mediatorLiveData.removeSource(source)
        })
    }

    fun submitStory(story: Story){
        var source = LiveDataReactiveStreams.fromPublisher<Response<Story>>(
            submitMergeRequestStoryApi.mergeRequest(storyId = story.id, content = story.content, token = token.token)
                .onErrorReturn {
                    Story(id = "-1", content = it.toString())
                }
                .map(Function<Story, Response<Story>>{
                    Log.d(TAG, "submitStory: ${it.content}")
                    if (it.id != "-1"){
                        Response.success(data = it)
                    }else Response.error(data = it, message = it.content)
                })
                .subscribeOn(Schedulers.io())
        )

        mediatorSubmitStory.addSource(source, Observer {
            mediatorSubmitStory.value = it
            mediatorSubmitStory.removeSource(source)
        })
    }
    fun observeComparative(): LiveData<Response<Comparative>>{
        return mediatorLiveData
    }
    fun observeSubmitStory(): LiveData<Response<Story>>{
        return mediatorSubmitStory
    }
}