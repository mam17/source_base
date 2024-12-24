package com.dtl.emojibatterywidget.component.main

import android.app.Activity
import android.content.Intent
import com.dtl.emojibatterywidget.base.activity.BaseActivity
import com.dtl.emojibatterywidget.databinding.ActivityMainBinding
import com.dtl.emojibatterywidget.utils.DialogEx.showDialogExit
import com.dtl.emojibatterywidget.utils.SpManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    @Inject
    lateinit var spManager: SpManager

    companion object {
        fun start(activity: Activity) {
            activity.startActivity(Intent(activity, MainActivity::class.java))
        }
    }

    override fun provideViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        super.initViews()
        setFullscreen()
    }

    override fun onBack() {
        showDialogExit(actionYes = { super.onBack() })
    }

}