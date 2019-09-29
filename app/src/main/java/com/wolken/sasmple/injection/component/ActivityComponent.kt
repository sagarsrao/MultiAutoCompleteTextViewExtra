package com.wolken.sasmple.injection.component

import com.wolken.sasmple.injection.PerActivity
import com.wolken.sasmple.injection.module.ActivityModule
import com.wolken.sasmple.features.base.BaseActivity
import com.wolken.sasmple.features.detail.DetailActivity
import com.wolken.sasmple.features.main.MainActivity
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(baseActivity: BaseActivity)

    fun inject(mainActivity: MainActivity)

    fun inject(detailActivity: DetailActivity)
}
