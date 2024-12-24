package com.dtl.emojibatterywidget.component.dialogs

import android.app.Activity
import android.util.Log
import com.dtl.emojibatterywidget.base.dialog.BaseDialog
import com.dtl.emojibatterywidget.databinding.DialogRateBinding

class DialogRate(
    private val activity: Activity,
    private val onRating: () -> Unit,
    private val onQuit: () -> Unit
) : BaseDialog<DialogRateBinding>(activity) {
    override fun provideViewBinding(): DialogRateBinding {
        return DialogRateBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        super.initViews()
        setOnCancelListener {
            val rating: Float = viewBinding.ratingBar.rating
            Log.d("Rating", "Rating value: $rating")

            if (rating == 0f) {
                return@setOnCancelListener
            }
            if (rating <= 3.0) {
                onQuit.invoke()
                dismiss()
            } else if (rating >= 4.0) {
                onRating.invoke()
                dismiss()
            }
        }
    }
}