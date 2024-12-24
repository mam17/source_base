package com.dtl.emojibatterywidget.component.onboarding

import android.content.Context
import android.content.Intent
import androidx.viewpager2.widget.ViewPager2
import com.dtl.emojibatterywidget.R
import com.dtl.emojibatterywidget.base.activity.BaseActivity
import com.dtl.emojibatterywidget.component.main.MainActivity
import com.dtl.emojibatterywidget.component.permission.PermissionActivity
import com.dtl.emojibatterywidget.databinding.ActivityOnboardingBinding
import com.dtl.emojibatterywidget.utils.SpManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnBoardingActivity : BaseActivity<ActivityOnboardingBinding>() {

    @Inject
    lateinit var spManager: SpManager

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, OnBoardingActivity::class.java))
        }
    }

    override fun provideViewBinding(): ActivityOnboardingBinding =
        ActivityOnboardingBinding.inflate(layoutInflater)

    private val onBoardingAdapter = OnBoardingAdapter()
    private var currentPosition = 0

    override var isReloadInter: Boolean = false

    override fun initViews() {
        setFullscreen()
        viewBinding.apply {
            vpOnBoarding.adapter = onBoardingAdapter

            vpOnBoarding.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    currentPosition = position

                    if (currentPosition == 3) {
                        viewBinding.buttonNext.setText(R.string.txt_get_start)
                    } else {
                        viewBinding.buttonNext.setText(R.string.txt_next)
                    }
                }
            })

            buttonNext.setOnClickListener {
                if (currentPosition < onBoardingAdapter.getListData().size - 1) {
                    vpOnBoarding.setCurrentItem(currentPosition + 1, true)
                } else {
                    MainActivity.start(this@OnBoardingActivity)
                    finish()
                }
            }

            dotIndicator.attachTo(vpOnBoarding)
            onBoardingAdapter.setData(
                listOf(
                    OnBoarding(
                        R.drawable.img_onb1,
                        R.string.onboarding_intro1
                    ),
                    OnBoarding(
                        R.drawable.img_onb2,
                        R.string.onboarding_intro2
                    ),
                    OnBoarding(
                        R.drawable.img_onb3,
                        R.string.onboarding_intro3
                    )
                )
            )


        }
    }

}