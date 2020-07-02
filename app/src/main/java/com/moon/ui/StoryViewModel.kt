package com.moon.ui
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.moon.model.LikeStory
import com.moon.model.Story
import com.moon.model.Token
import com.moon.network.story.GetStoriesApi
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import io.reactivex.functions.Function
import java.util.concurrent.TimeUnit

class StoryViewModel @Inject constructor(private var getStoriesApi: GetStoriesApi): ViewModel(){
    var mediatorLiveData: MediatorLiveData<Response<ArrayList<Story>>> = MediatorLiveData()
    var mediatorClapLiveData: MediatorLiveData<Response<LikeStory>> = MediatorLiveData()

    @Inject lateinit var token: Token

    fun getStories(story: Story){
        var source = LiveDataReactiveStreams.fromPublisher<Response<ArrayList<Story>>> (
            getStoriesApi.getStories(search = story.title, token = token.token)
                .onErrorReturn {
                    Log.d("TAG", "getStories: error return: ${it.message}")
                    ArrayList<Story>().also { story ->
                        story.add(Story().also { it.id = "-1" })
                    }
                }
                .map(Function<ArrayList<Story>, Response<ArrayList<Story>>>{
                    Log.d("TAG", "getStories:1 ${it[0].title}")
                    if (it != null && it.size > 0){
                        if (it[0].id == "-1")
                            Response.error(data = it, message = "error")
                        else Response.success(data = it)
                    }else Response.success(data = it)
                })
                .subscribeOn(Schedulers.io())
            )

        mediatorLiveData.addSource(source, Observer {
            mediatorLiveData.value = it
            mediatorLiveData.removeSource(source)
        })
    }

    fun clapStory(storyId: String, clapCount: Int){
        var source = LiveDataReactiveStreams.fromPublisher<Response<LikeStory>>(
            getStoriesApi.likeStory(storyId = storyId, token = token.token, clap = clapCount)
                .onErrorReturn {
                    LikeStory().also { it.id = -1 }
                }
                .map(Function<LikeStory, Response<LikeStory>>{
                    if (it.id == -1)
                        Response.error(message = "error")
                    else Response.success(data = it)
                })
                .debounce(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
        )

        mediatorClapLiveData.addSource(source, Observer {
            mediatorClapLiveData.value = it
            mediatorLiveData.removeSource(source)
        })
    }

    fun createStory(story: Story){

    }
    fun observeClap(): MediatorLiveData<Response<LikeStory>> {
        return mediatorClapLiveData
    }
    fun observe(): LiveData<Response<ArrayList<Story>>>{
        return mediatorLiveData
    }
}