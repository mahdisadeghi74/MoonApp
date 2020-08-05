package com.moon.ui.joinStory

import android.util.Log
import androidx.lifecycle.*
import com.moon.model.JoinStory
import com.moon.model.Story
import com.moon.model.Token
import com.moon.network.story.ChangeJoinStoryStatusApi
import com.moon.network.story.GetAllJoinRequests
import com.moon.network.story.JoinStoryApi
import com.moon.ui.Response
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.functions.Function
import rx.schedulers.Schedulers
import javax.inject.Inject

class JoinStoryViewModel @Inject constructor(
    private var joinStoryApi: JoinStoryApi,
    private var getAllJoinRequests: GetAllJoinRequests,
    private var changeJoinStoryStatusApi: ChangeJoinStoryStatusApi
) : ViewModel() {
    private var joinRequestes: MediatorLiveData<Response<ArrayList<JoinStory>>> = MediatorLiveData()
    private var joinRequesteStatus: MediatorLiveData<Response<JoinStory>> = MediatorLiveData()

    @Inject
    lateinit var token: Token

    fun getAllJoinRequests(storyId: String) {
        var source = LiveDataReactiveStreams.fromPublisher<Response<ArrayList<JoinStory>>>(
            getAllJoinRequests.joinStory(storyId = storyId, token = token.token)
                .onErrorReturn {
                    Log.d("TAG", "getAllJoinRequestsError: ${it.message}")
                    ArrayList<JoinStory>().also { joinStory ->
                        joinStory.add(JoinStory().also { it.id = "-1" })
                    }
                }
                .map(Function<ArrayList<JoinStory>, Response<ArrayList<JoinStory>>> {
                    Log.d("TAG", "getAllJoinRequests:1 ${it.size}")
                    if (it.size > 0) {
                        if (it[0].id == "-1")
                            Response.error(data = it, message = "error")
                        else Response.success(data = it)
                    } else Response.success(data = it)
                })
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
        )

        joinRequestes.addSource(source, androidx.lifecycle.Observer {
            joinRequestes.value = it
            joinRequestes.removeSource(source)
        })
    }

    fun changeJoinStoryStatus(joinStory: JoinStory, storyId: String) {
        var source = LiveDataReactiveStreams.fromPublisher<Response<JoinStory>>(
            changeJoinStoryStatusApi.joinStory(
                id = joinStory.id,
                storyId = storyId,
                memberId = joinStory.userId,
                isAccepted = joinStory.isAccepted,
                isActive = joinStory.isActive,
                token = token.token
            )
                .onErrorReturn {
                    JoinStory(id = "-1")
                }
                .map(Function<JoinStory, Response<JoinStory>> {
                    Log.d("TAG", "setAdapter: ${joinStory.isAccepted}")
                    if (it.id == "-1")
                        Response.error(data = it, message = "error")
                    else Response.success(data = it)
                })
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
        )

        joinRequesteStatus.addSource(source, Observer {
            joinRequesteStatus.value = it
            joinRequesteStatus.removeSource(source)
        })

    }

    fun observeRequests(): LiveData<Response<ArrayList<JoinStory>>> {
        return joinRequestes;
    }

    fun observeChangeStatus(): LiveData<Response<JoinStory>>{
        return joinRequesteStatus
    }
}