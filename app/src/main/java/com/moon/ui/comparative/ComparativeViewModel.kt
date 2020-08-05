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

class ComparativeViewModel @Inject constructor(private var comparisonTwoStoriesApi: ComparisonTwoStoriesApi): ViewModel() {
    var mediatorLiveData = MediatorLiveData<Response<Comparative>>()

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

    fun observeComparative(): LiveData<Response<Comparative>>{
        return mediatorLiveData
    }
}