package com.moon.ui.auth

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.*
import com.moon.model.Token

import com.moon.network.auth.AuthApi
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AuthViewModel @Inject constructor(private var authApi: AuthApi) : ViewModel() {
    private var mediatorLiveData: MediatorLiveData<AuthResponse<Token>> = MediatorLiveData()

    init {

    }

    fun login(email: String, password: String): Unit {
       mediatorLiveData.value = AuthResponse.loading(date = null)
        val source: LiveData<AuthResponse<Token>> = LiveDataReactiveStreams.fromPublisher(
            authApi.authentication(email, password)
                .onErrorReturn {
                    Token().also { token: Token ->
                        if (it.localizedMessage == "HTTP 400 Bad Request") {
                            token.token = "-1"
                            token.message = "Username or password is wrong"
                        }else{
                            token.token = "-1"
                            token.message = "Server error"
                        }
                    }
                }

                .map(Function<Token, AuthResponse<Token>>{
                    if (it.token == "-1")
                        AuthResponse.error(message = it.message)
                    else AuthResponse.authenticated(date = it)
                })
                .subscribeOn(Schedulers.io())
        )

        mediatorLiveData.addSource(source, Observer {
            mediatorLiveData.value = it
            mediatorLiveData.removeSource(source)
        })
    }

    fun observe(): LiveData<AuthResponse<Token>> {
        return mediatorLiveData
    }


}