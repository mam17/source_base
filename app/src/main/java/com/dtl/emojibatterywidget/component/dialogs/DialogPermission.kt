package com.dtl.emojibatterywidget.component.dialogs

import android.content.Context
import com.dtl.emojibatterywidget.base.dialog.BaseDialog
import com.dtl.emojibatterywidget.databinding.DialogPermissionBinding

class DialogPermission(context: Context) :
    BaseDialog<DialogPermissionBinding>(context) {
    var gotoSettingAction: (() -> Unit)? = null
    override fun provideViewBinding(): DialogPermissionBinding {
        return DialogPermissionBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        super.initViews()
        viewBinding.apply {
            btnGotoSetting.setOnClickListener {
                gotoSettingAction?.invoke()
                dismiss()
            }
            btnCancel.setOnClickListener { dismiss() }
        }
    }
}