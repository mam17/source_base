package com.dtl.emojibatterywidget.component.dialogs

import android.app.Activity
import com.dtl.emojibatterywidget.base.dialog.BaseDialog
import com.dtl.emojibatterywidget.databinding.DialogSuccessBinding

class DialogSuccess(activity: Activity) :
    BaseDialog<DialogSuccessBinding>(activity) {

    var actionClose: (()->Unit)? = null
    override fun provideViewBinding(): DialogSuccessBinding {
        return DialogSuccessBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        super.initViews()
        viewBinding.apply {
            btnClose.setOnClickListener {
                actionClose?.invoke()
                dismiss()
            }
        }
    }
}