package com.moon.di

import android.app.Activity
import android.app.ActivityOptions
import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.Nullable
import com.moon.model.Token
import com.moon.util.Constants
import dagger.Module
import dagger.Provides
import dagger.android.support.DaggerAppCompatActivity
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {


    @Provides
    fun providerRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun providerSharedPreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences(Constants.SHARED_PREFERENCES_KEY, MODE_PRIVATE)
    }

    @Provides
    fun providerToken(sharedPreferences: SharedPreferences): Token{
        return Token().also { it.token = "Token ${sharedPreferences.getString(Constants.TOKEN_KEY, "")}"}
    }
}