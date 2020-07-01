package com.moon.di.auth

import com.moon.network.auth.AuthApi
import com.moon.network.auth.RegisterApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module
class AuthModule {

    @Provides
    fun providerAuthApi(retrofit: Retrofit): AuthApi{
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    fun providerRegister(retrofit: Retrofit): RegisterApi{
        return retrofit.create(RegisterApi::class.java)
    }
}