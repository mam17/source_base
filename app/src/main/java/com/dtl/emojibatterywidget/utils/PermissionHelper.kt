package com.dtl.emojibatterywidget.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dtl.emojibatterywidget.utils.Constant.REQUEST_PERMISSION_CAMERA

object PermissionHelper {

    fun requestPermissionCamera(context: Context) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.CAMERA),
            REQUEST_PERMISSION_CAMERA
        )
    }

    fun checkPermissionCamera(context: Context) = arrayOf(Manifest.permission.CAMERA).all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }


    fun goToSystem(context: Context, requestPermissionLauncher: ActivityResultLauncher<Intent>) {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri: Uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        requestPermissionLauncher.launch(intent)
    }

    fun startForResult(
        activity: Activity,
        requestLocationPermissionLauncher: ActivityResultLauncher<Intent> ){
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri: Uri = Uri.fromParts("package", activity.packageName, null)
        intent.data = uri
        requestLocationPermissionLauncher.launch(intent)
    }

}