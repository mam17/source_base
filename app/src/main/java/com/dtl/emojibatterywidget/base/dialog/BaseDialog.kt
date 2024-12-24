package com.dtl.emojibatterywidget.base.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.widget.RelativeLayout
import androidx.viewbinding.ViewBinding

abstract class BaseDialog<V : ViewBinding>(context: Context) :
    Dialog(context) {
    lateinit var viewBinding: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = provideViewBinding()
        setContentView(viewBinding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setLayout(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        window?.setGravity(Gravity.CENTER)
        initViews()
        initData()
    }

    abstract fun provideViewBinding(): V
    open fun initViews() {}
    open fun initData() {}
}