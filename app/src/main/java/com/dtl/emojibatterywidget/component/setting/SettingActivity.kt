package com.dtl.emojibatterywidget.component.setting

import android.app.Activity
import android.content.Intent
import com.dtl.emojibatterywidget.R
import com.dtl.emojibatterywidget.base.activity.BaseActivity
import com.dtl.emojibatterywidget.component.dialogs.DialogRate
import com.dtl.emojibatterywidget.component.language.LanguageActivity
import com.dtl.emojibatterywidget.databinding.ActivitySettingBinding
import com.dtl.emojibatterywidget.utils.AppEx.openAppInStore
import com.dtl.emojibatterywidget.utils.AppEx.shareApp
import com.dtl.emojibatterywidget.utils.AppEx.showPolicyApp
import com.dtl.emojibatterywidget.utils.SpManager
import com.dtl.emojibatterywidget.utils.ViewEx.gone
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingActivity : BaseActivity<ActivitySettingBinding>() {
    @Inject
    lateinit var spManager: SpManager

    companion object {
        fun start(activity: Activity) {
            activity.startActivity(Intent(activity, SettingActivity::class.java))
        }
    }

    override fun provideViewBinding(): ActivitySettingBinding {
        return ActivitySettingBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        super.initViews()
        setFullscreen()
        viewBinding.apply {
            if (spManager.isRated(this@SettingActivity)) {
                btnRateUs.gone()
            }
            btnLanguage.setOnClickListener {
                startActivity(
                    Intent(
                        this@SettingActivity,
                        LanguageActivity::class.java
                    )
                )
            }
            btnClose.setOnClickListener {
                onBack()
            }
            btnPrivacyPolicy.setOnClickListener { showPolicyApp() }
            btnRateUs.setOnClickListener { actionRate() }
            btnShareApp.setOnClickListener { shareApp() }
        }
    }

    private fun actionRate() {
        val dialogRate = DialogRate(this,
            onRating = {
                viewBinding.btnRateUs.gone()
                spManager.forceRated(this)
                showToast(getString(R.string.title_rate))
                openAppInStore()
            },
            onQuit = { showToast(getString(R.string.title_rate)) })
        dialogRate.show()
    }

}