package com.moon.di

import android.app.Application
import com.moon.ui.story.activity.StoryActivity
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication

@Component(modules = [AndroidSupportInjectionModule::class, ActivityBuilderModule::class, ViewModelFactoryModule::class, AppModule::class])
interface AppComponent : AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}