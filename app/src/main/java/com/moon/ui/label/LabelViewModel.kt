package com.moon.ui.label

import android.util.Log
import androidx.lifecycle.*
import com.moon.model.Label
import com.moon.model.Token
import com.moon.network.label.GetLabelsApi
import com.moon.ui.Response
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LabelViewModel @Inject constructor(private var getLabelsApi: GetLabelsApi): ViewModel() {
    var mediatorLiveData = MediatorLiveData<Response<ArrayList<Label>>>()
    @Inject
    lateinit var token: Token

    fun getLabels(){
        var source = LiveDataReactiveStreams.fromPublisher<Response<ArrayList<Label>>>(
            getLabelsApi.getLabels(token = token.token, search = "")
                .onErrorReturn {
                    Log.d("TAG", "observe:label ${it.message}")
                    ArrayList<Label>()
                }
                .map(Function<ArrayList<Label>, Response<ArrayList<Label>>>{
                    Log.d("TAG", "observe:label ${it}")
                    Response.success(data = it)
                })
                .subscribeOn(Schedulers.io())
        )

        mediatorLiveData.addSource(source, Observer {
            mediatorLiveData.value = it
            mediatorLiveData.removeSource(source)
        })
    }


    fun observe(): LiveData<Response<ArrayList<Label>>>{
        return mediatorLiveData
    }
}