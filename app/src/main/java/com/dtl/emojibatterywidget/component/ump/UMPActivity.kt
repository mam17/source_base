package com.dtl.emojibatterywidget.component.ump

import android.os.Build
import android.view.WindowInsets
import android.view.WindowInsetsController
import com.dtl.emojibatterywidget.App
import com.dtl.emojibatterywidget.base.activity.BaseActivity
import com.dtl.emojibatterywidget.component.splash.SplashActivity
import com.dtl.emojibatterywidget.databinding.ActivityUmpBinding
import com.dtl.emojibatterywidget.utils.SpManager


class UMPActivity : BaseActivity<ActivityUmpBinding>() {

    override fun provideViewBinding(): ActivityUmpBinding =
        ActivityUmpBinding.inflate(layoutInflater)

    override fun initViews() {
        super.initViews()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.let {
                it.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }

    override fun initData() {
        super.initData()

//        if (SpManager.getInstance(this).isUMPShowed()) {
//            RemoteConfig.instance().fetch()
//            openSplash();
//        } else {
//            RemoteConfig.instance().fetch {
//                initUmp()
//            }
//        }
    }

    private fun openSplash() {

        val app: App = application as App
        app.initAds()

        SpManager.getInstance(this).setUMPShowed(true)
        SplashActivity.start(this);
        finish()
    }

    private fun initUmp() {
//        UMPUtils.init(this@UMPActivity, nextAction = {
//            openSplash()
//        }, reset = true, showLoading = false)
    }
}