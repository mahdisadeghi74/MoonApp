package com.moon.ui.auth

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.moon.model.Account
import com.moon.model.Token
import io.reactivex.functions.Function
import com.moon.network.auth.RegisterApi
import com.moon.ui.Response
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AccountViewModel @Inject constructor(private var registerApi: RegisterApi): ViewModel() {
    private var mediatorLiveData = MediatorLiveData<Response<Account>>()

    fun register(email: String, password: String, password2: String, userName: String){
        mediatorLiveData.value = Response.loading()

        val source = LiveDataReactiveStreams.fromPublisher<Response<Account>> {
            registerApi.register(email = email, password = password, password2 = password2, username = userName)
                .onErrorReturn { error ->
                    Account().also {
                        it.pk = "-1"
                        it.message = error.localizedMessage
                    }
                }
                .map(Function<Account, Response<Account>> {
                    if (it.pk == "-1")
                        Response.error(message = it.message)
                    else{
                        Log.d(TAG, "register d: response ${it.username}")
                        Response.success(data = it)
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe ({
                    Log.d(TAG, "register: response ${it.data?.username}")
                },{
                    Log.d(TAG, "register: error ${it.message}")
                })

    }

        mediatorLiveData.addSource(source) {
            mediatorLiveData.value = it
            mediatorLiveData.removeSource(source)
        }
    }

    fun observe(): LiveData<Response<Account>>{
        return mediatorLiveData
    }
}
