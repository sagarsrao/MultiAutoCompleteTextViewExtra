package com.wolken.sasmple.injection.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import com.wolken.sasmple.injection.ApplicationContext

@Module(includes = arrayOf(ApiModule::class))
class AppModule(private val application: Application) {

    @Provides
    internal fun provideApplication(): Application {
        return application
    }

    @Provides
    @ApplicationContext
    internal fun provideContext(): Context {
        return application
    }
}