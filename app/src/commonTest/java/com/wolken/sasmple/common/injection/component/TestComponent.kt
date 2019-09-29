package com.wolken.sasmple.common.injection.component

import dagger.Component
import com.wolken.sasmple.common.injection.module.ApplicationTestModule
import com.wolken.sasmple.injection.component.AppComponent
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationTestModule::class))
interface TestComponent : AppComponent