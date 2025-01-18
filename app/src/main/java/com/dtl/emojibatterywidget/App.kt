package com.dtl.emojibatterywidget

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.dtl.emojibatterywidget.utils.AppEx.getDeviceLanguage
import com.dtl.emojibatterywidget.utils.AppEx.setAppLanguage
import com.dtl.emojibatterywidget.utils.LocaleHelper
import com.dtl.emojibatterywidget.utils.NetworkUtil
import com.dtl.emojibatterywidget.utils.SpManager
import dagger.hilt.android.HiltAndroidApp
import org.json.JSONException
import org.json.JSONObject
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@HiltAndroidApp
@Singleton
class App : Application(), Application.ActivityLifecycleCallbacks, DefaultLifecycleObserver {
    companion object {
        @SuppressLint("StaticFieldLeak")
        var instance: App? = null

        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
    }

    @Inject
    lateinit var spManager: SpManager

    private var currentActivity: Activity? = null

    override fun onCreate() {
        super<Application>.onCreate()
        instance = this

        registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        NetworkUtil.initNetwork(this)
    }

    fun initAds() {
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        val locale = getDeviceLanguage()
        val language = spManager.getLanguage()
        LocaleHelper.onAttach(this, language.languageCode)
        super.onConfigurationChanged(newConfig)
    }

    override fun attachBaseContext(newBase: Context?) {
        val languageCode = newBase?.let { SpManager.getInstance(it).getLanguage().languageCode }
        val context = languageCode?.let { newBase.setAppLanguage(it) }
        super.attachBaseContext(context)
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity) {
        currentActivity = activity
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }
}