package com.enesgemci.mamasandpapas.ui

import android.content.Intent
import com.enesgemci.mamasandpapas.base.BaseActivity
import com.enesgemci.mamasandpapas.dagger.component.ActivityComponent
import javax.inject.Inject

internal class MainActivity : BaseActivity<MainActivityView, MainActivityPresenter>(), MainActivityView {

    @Inject
    lateinit var mPresenter: MainActivityPresenter

    override fun onActivityStarted(intent: Intent) {
        presenter.onActivityStarted()
    }

    override fun createPresenter(): MainActivityPresenter {
        return mPresenter
    }

    override fun injectActivity(component: ActivityComponent) {
        component.inject(this)
    }
}