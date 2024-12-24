package com.dtl.emojibatterywidget.component.language

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.dtl.emojibatterywidget.R
import com.dtl.emojibatterywidget.base.activity.BaseActivity
import com.dtl.emojibatterywidget.component.main.MainActivity
import com.dtl.emojibatterywidget.component.onboarding.OnBoardingActivity
import com.dtl.emojibatterywidget.databinding.ActivityLanguageBinding
import com.dtl.emojibatterywidget.utils.AppEx.setAppLanguage
import com.dtl.emojibatterywidget.utils.SpManager
import com.dtl.emojibatterywidget.utils.ViewEx.gone
import com.dtl.emojibatterywidget.utils.ViewEx.invisible
import com.dtl.emojibatterywidget.utils.ViewEx.visible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LanguageActivity : BaseActivity<ActivityLanguageBinding>() {
    private val viewModel: LanguageViewModel by viewModels()
    private val languageAdapter = LanguageAdapter()

    @Inject
    lateinit var spManager: SpManager

    override fun provideViewBinding(): ActivityLanguageBinding {
        return ActivityLanguageBinding.inflate(layoutInflater)
    }

    override fun initViews() {

        val fromSplash = intent.getBooleanExtra(KEY_FROM_SPLASH, false)
        if (!fromSplash) {
            viewBinding.toolBarLanguage.imvBack.visible()
        } else {
            viewBinding.toolBarLanguage.imvBack.gone()
        }

        viewBinding.toolBarLanguage.tvTitle.text = getString(R.string.language)

        viewBinding.toolBarLanguage.btnSelect.invisible()

        setStatusBarColor(R.color.color_bg)

        viewBinding.toolBarLanguage.imvBack.setOnClickListener {
            finish()
        }

        viewBinding.rclLanguage.adapter = languageAdapter
        languageAdapter.onClick = {
            languageAdapter.selectLanguage(it.languageCode)
            viewBinding.toolBarLanguage.btnSelect.visible()
        }
        viewBinding.toolBarLanguage.btnSelect.setOnClickListener {
            languageAdapter.selectedLanguage()?.let { languageModel ->
                spManager.saveLanguage(languageModel)
                setAppLanguage(languageModel.languageCode)
                val fromSplash = intent.getBooleanExtra(KEY_FROM_SPLASH, false)
                if (fromSplash) {
                    OnBoardingActivity.start(this)
                    finish()
                } else {
                    startActivity(Intent(this, MainActivity::class.java).also {
                        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    })
                }
            }
        }

        if (spManager.getFirstOpenApp()) {
            viewBinding.toolBarLanguage.btnSelect.setText(R.string.txt_next)
        }

    }


    override fun initObserver() {
        viewModel.listLanguage.observe(this) { listLang ->
            languageAdapter.setData(ArrayList(listLang))
            val currentLanguage = spManager.getLanguage()
            listLang.find { it.languageCode == currentLanguage.languageCode }?.selected = true
        }

    }

    override fun initData() {
        viewModel.loadListLanguage()
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onDestroy() {

        super.onDestroy()
    }

    companion object {
        const val KEY_FROM_SPLASH = "key_splash"
        fun start(context: Context, fromSplash: Boolean = false) {
            Intent(context, LanguageActivity::class.java).also {
                it.putExtra(KEY_FROM_SPLASH, fromSplash)
                context.startActivity(it)
            }
        }
    }

}