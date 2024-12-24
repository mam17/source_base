package com.dtl.emojibatterywidget.component.dialogs

import android.content.Context
import com.dtl.emojibatterywidget.base.dialog.BaseDialog
import com.dtl.emojibatterywidget.databinding.DialogExitBinding

class DialogExit(context: Context) :
    BaseDialog<DialogExitBinding>(context) {
    var yesOnClick: (() -> Unit)? = null
    override fun provideViewBinding(): DialogExitBinding {
        return DialogExitBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        super.initViews()
        viewBinding.apply {
            btnYes.setOnClickListener {
                yesOnClick?.invoke()
                dismiss()
            }
            btnCancel.setOnClickListener { dismiss() }
        }
    }
}