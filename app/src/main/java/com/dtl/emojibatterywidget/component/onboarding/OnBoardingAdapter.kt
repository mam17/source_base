package com.dtl.emojibatterywidget.component.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dtl.emojibatterywidget.base.adapter.BaseAdapter
import com.dtl.emojibatterywidget.databinding.ItemOnboardingBinding

class OnBoardingAdapter : BaseAdapter<OnBoarding, ItemOnboardingBinding>() {

    override fun binData(viewBinding: ItemOnboardingBinding, item: OnBoarding, position: Int) {
        viewBinding.apply {
            imgBoarding.setImageResource(item.resImage)
            tvOnboarding.text = root.context.resources.getString(item.resDescription)
        }
    }

    override fun provideViewBinding(parent: ViewGroup): ItemOnboardingBinding =
        ItemOnboardingBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
}