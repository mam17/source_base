package com.dtl.emojibatterywidget.utils

import android.app.Activity
import com.dtl.emojibatterywidget.component.dialogs.DialogExit
import com.dtl.emojibatterywidget.component.dialogs.DialogPermission
import com.dtl.emojibatterywidget.component.dialogs.DialogSuccess

object DialogEx {

    fun showDialogSuccess(activity: Activity, actionClose: () -> Unit) {
        val dialog = DialogSuccess(activity)
        dialog.actionClose = {
            actionClose.invoke()
        }
        dialog.show()
    }

    fun Activity.showDialogPermission(actionGotoSetting: (() -> Unit)) {
        val dialogDelete = DialogPermission(this)
        dialogDelete.show()
        dialogDelete.gotoSettingAction = {
            actionGotoSetting.invoke()
        }
    }
    fun Activity.showDialogExit(actionYes: (() -> Unit)) {
        val dialogExit = DialogExit(this)
        dialogExit.show()
        dialogExit.yesOnClick = {
            actionYes.invoke()
        }
    }
}