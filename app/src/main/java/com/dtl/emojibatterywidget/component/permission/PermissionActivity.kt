package com.dtl.emojibatterywidget.component.permission

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.dtl.emojibatterywidget.R
import com.dtl.emojibatterywidget.base.activity.BaseActivity
import com.dtl.emojibatterywidget.component.main.MainActivity
import com.dtl.emojibatterywidget.databinding.ActivityPermissonBinding
import com.dtl.emojibatterywidget.utils.Constant.REQUEST_PERMISSION_CAMERA
import com.dtl.emojibatterywidget.utils.DialogEx.showDialogPermission
import com.dtl.emojibatterywidget.utils.PermissionHelper
import com.dtl.emojibatterywidget.utils.PermissionHelper.checkPermissionCamera
import com.dtl.emojibatterywidget.utils.PermissionHelper.requestPermissionCamera
import com.dtl.emojibatterywidget.utils.SpManager
import com.dtl.emojibatterywidget.utils.ViewEx.gone
import com.dtl.emojibatterywidget.utils.ViewEx.visible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PermissionActivity : BaseActivity<ActivityPermissonBinding>() {
    private var isCheckCamera: Boolean = false

    @Inject
    lateinit var spManager: SpManager

    companion object {
        fun start(activity: Activity) {
            activity.startActivity(Intent(activity, PermissionActivity::class.java))
        }
    }

    private val requestCameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (checkPermissionCamera(this)) {
            isCheckCamera = true
        } else {
            isCheckCamera = false
        }
        updateSwitch()
    }

    override fun provideViewBinding(): ActivityPermissonBinding {
        return ActivityPermissonBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        super.initViews()
        setFullscreen()
        viewBinding.run {

            llCamera.setOnClickListener {
                requestPermissionCamera(this@PermissionActivity)
            }

            tvNext.setOnClickListener {
                MainActivity.start(this@PermissionActivity)
                spManager.saveFirstOpenApp()
                spManager.saveStatePermission()
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        updateSwitch()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_CAMERA) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                isCheckCamera = true
            } else {
                isCheckCamera = false
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.CAMERA
                    )
                ) {
                    showToast(getString(R.string.txt_some_functions_require_permission_camera))
                } else {
                    showDialogPermission {
                        PermissionHelper.goToSystem(this, requestCameraPermissionLauncher)
                    }
                }
            }
            updateSwitch()
        }
    }

    private fun updateSwitch() {
        viewBinding.sw1.isChecked = isCheckCamera
        viewBinding.llCamera.isEnabled = !isCheckCamera

        if (viewBinding.sw1.isChecked) {
            viewBinding.tvNext.visible()
        } else {
            viewBinding.tvNext.gone()
        }
    }

}
