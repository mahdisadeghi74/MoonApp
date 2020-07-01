package com.dependencies.buria.android.support.daggerktx2.viewmodel

import androidx.annotation.Nullable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moon.di.auth.AuthScope
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

class ViewModelProvidersFactory @Inject constructor(var creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>): ViewModelProvider.Factory {

    @Nullable
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        var creator: Provider<ViewModel>? = creators[modelClass]

        for (entry in creators.entries){
            if (modelClass.isAssignableFrom(entry.key)){
                creator = entry.value
                break
            }
        }

        if (creator == null) {
            throw IllegalArgumentException ("unknown model class $modelClass");
        }

        try {
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException (e);
        }
    }

}