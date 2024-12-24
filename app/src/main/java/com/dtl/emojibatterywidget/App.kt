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
import com.dtl.emojibatterywidget.utils.LocaleHelper
import com.dtl.emojibatterywidget.utils.NetworkUtil
import com.dtl.emojibatterywidget.utils.SpManager
import dagger.hilt.android.HiltAndroidApp
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

    var isShowLoadingAd = false

    @Inject
    lateinit var spManager: SpManager

    private var currentActivity: Activity? = null


    override fun onCreate() {
        super<Application>.onCreate()
        instance = this

//        FirebaseApp.initializeApp(this)

        registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        NetworkUtil.initNetwork(this)
    }

    fun initAds() {
//        MobileAds.initialize(this)
//        val requestConfiguration = RequestConfiguration.Builder().build()
//        MobileAds.setRequestConfiguration(requestConfiguration)
//
//        if(spManager.getBoolean(NameRemoteAdmob.APP_RESUME, true)){
//            openAdmob = OpenAdmob(this, BuildConfig.open_resume)
//        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        val language = spManager.getLanguage()
        LocaleHelper.onAttach(this, language.languageCode)
        super.onConfigurationChanged(newConfig)
    }

    override fun attachBaseContext(base: Context?) {
        val context = LocaleHelper.onAttach(base, Locale.getDefault().language)
        super.attachBaseContext(context)
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
//        Log.i("TAG_ONSTART", "onStart: $isShowLoadingAd")
//        if(spManager.getBoolean(NameRemoteAdmob.APP_RESUME, true) && !isShowLoadingAd){
//            openAdmob?.run {
//                currentActivity?.let {
//                    showAdIfAvailable(it)
//                }
//            }
//        }
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