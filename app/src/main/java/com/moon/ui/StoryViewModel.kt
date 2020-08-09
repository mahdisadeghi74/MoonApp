package com.moon.ui

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.moon.model.LikeStory
import com.moon.model.Story
import com.moon.model.StringResponse
import com.moon.model.Token
import com.moon.network.story.*
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import io.reactivex.functions.Function
import java.util.concurrent.TimeUnit
import kotlin.math.log

class StoryViewModel @Inject constructor(private var getStoriesApi: GetStoriesApi, private var createStoryApi: CreateStoryApi,
                                         private var createStoryBranchApi: CreateStoryBranchApi,
                                         private var joinStoryApi: JoinStoryApi,
                                         private var mergeRequestStoryApi: MergeRequestStoryApi
) : ViewModel() {
    var mediatorLiveData: MediatorLiveData<Response<ArrayList<Story>>> = MediatorLiveData()
    var mediatorClapLiveData: MediatorLiveData<Response<LikeStory>> = MediatorLiveData()
    var mediatorAddStory: MediatorLiveData<Response<Story>> = MediatorLiveData()

    @Inject
    lateinit var token: Token

    fun getStories(story: Story, search: String ?= null){
        var source = LiveDataReactiveStreams.fromPublisher<Response<ArrayList<Story>>> (
            getStoriesApi.getStories(search = search, parentId = story.id, token = token.token)
                .onErrorReturn {
                    Log.d("TAG", "getStories: error return: ${it.message}")
                    ArrayList<Story>().also { story ->
                        story.add(Story().also { it.id = "-1" })
                    }
                }
                .map(Function<ArrayList<Story>, Response<ArrayList<Story>>>{
                    Log.d("TAG", "getStories:1 ${it.size}")
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

    fun clapStory(storyId: String, clapCount: Int) {
        val source = LiveDataReactiveStreams.fromPublisher<Response<LikeStory>>(
            getStoriesApi.likeStory(storyId = storyId, token = token.token, clap = clapCount)
                .onErrorReturn {
                    LikeStory().also { it.id = -1 }
                }
                .map(Function<LikeStory, Response<LikeStory>> {
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

    fun createStory(story: Story) {
        val labels = arrayListOf<String>()
        story.label.forEach { labels.add(it.name) }
        val source = LiveDataReactiveStreams.fromPublisher<Response<Story>>(
            createStoryApi.createStory(token = token.token,
                title = story.title,
                category = story.category?.id,
                content = story.content,
                branch = story.branch,
                currentStory = story.id,
                label = labels
            )
                .onErrorReturn {
                    Story().also {
                        it.id = "-1"
                        it.title = it.toString()
                    }
                }
                .map(Function<Story, Response<Story>>{
                    if (it.id == "-1")
                        Response.error(message = it.title)
                    else Response.success(data = story)
                })
                .subscribeOn(Schedulers.io())
        )
        mediatorAddStory.addSource(source, Observer {
            mediatorAddStory.value = it;
            mediatorAddStory.removeSource(source)
        })
    }

    fun createBranch(storyId: String, branchName: String) {
        val source = LiveDataReactiveStreams.fromPublisher<Response<Story>>(
            createStoryBranchApi.createStoryBranch(storyId = storyId, branch = branchName, token = token.token)
                .onErrorReturn {throwable ->
                    Story().also {
                        it.id = "-1"
                        it.title = throwable.toString()
                    }
                }
                .map(Function<Story, Response<Story>>{
                    Log.d(TAG, "createBranch: ${it.title}")
                    if (it.id == "-1")
                        Response.error(message = it.title)
                    else Response.success(data = it)
                })
                .subscribeOn(Schedulers.io())
        )
        mediatorAddStory.addSource(source, Observer {
            mediatorAddStory.value = it;
            mediatorAddStory.removeSource(source)
        })
    }

    @SuppressLint("CheckResult")
    fun joinStory(storyId: String){
        joinStoryApi.joinStory(storyId=storyId, token = token.token)
            .subscribeOn(Schedulers.io())
            .onErrorReturn {
                StringResponse(it.toString())
            }
            .subscribe({
                Log.d(TAG, "joinStory: ${it.result}")
            },{
                Log.d(TAG, "joinStory: $it")
            })
    }

    fun mergeRequestStory(story: Story){
        val source = LiveDataReactiveStreams.fromPublisher<Response<Story>> (
            mergeRequestStoryApi.mergeRequest(storyId = story.id, content = story.content, token = token.token)
                .onErrorReturn {throwable ->
                    Log.d(TAG, "mergeRequestStory: error: $throwable")
                    Story().also {
                        it.id = "-1"
                        it.title = throwable.toString()
                    }
                }
                .map(Function<Story, Response<Story>>{
                    Log.d(TAG, "mergeRequestStory: ${it.title}")
                    if (it.id == "-1")
                        Response.error(message = it.title)
                    else Response.success(data = it)
                })
                .subscribeOn(Schedulers.io())
        )

        mediatorAddStory.addSource(source, Observer {
            mediatorAddStory.value = it
            mediatorAddStory.removeSource(source)
        })
    }
    fun observeClap(): MediatorLiveData<Response<LikeStory>> {
        return mediatorClapLiveData
    }

    fun observe(): LiveData<Response<ArrayList<Story>>> {
        return mediatorLiveData
    }
    fun observeAddStory(): LiveData<Response<Story>> {
        return mediatorAddStory
    }
}