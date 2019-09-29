package com.wolken.sasmple.injection.component

import com.wolken.sasmple.injection.PerFragment
import com.wolken.sasmple.injection.module.FragmentModule
import dagger.Subcomponent

/**
 * This component inject dependencies to all Fragments across the application
 */
@PerFragment
@Subcomponent(modules = [FragmentModule::class])
interface FragmentComponent