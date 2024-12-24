package com.dtl.emojibatterywidget.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.dtl.emojibatterywidget.BuildConfig
import com.dtl.emojibatterywidget.R
import com.dtl.emojibatterywidget.domain.layer.LanguageModel
import com.google.gson.Gson
import java.util.Locale


object AppEx {
    fun LanguageModel.toJson(): String {
        return Gson().toJson(this)
    }

    fun String.toLanguageModel(): LanguageModel? {
        return kotlin.runCatching {
            Gson().fromJson(this, LanguageModel::class.java)
        }.getOrNull()
    }

    fun Context.setAppLanguage(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    fun Context.getDeviceLanguage(): String {
        return Locale.getDefault().language
    }

    fun Context.openAppInStore() {
        val uri = Uri.parse("market://details?id=" + this.packageName)
        val myAppLinkToMarket = Intent(Intent.ACTION_VIEW, uri)
        try {
            startActivity(myAppLinkToMarket)
        } catch (e: ActivityNotFoundException) {
            Log.i("TAGaa", "openAppInStore: ")
        }
    }

    fun Context.showPolicyApp() {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data =
            Uri.parse("https://sites.google.com/view/transparent-live-wallpaper1/trang-ch%E1%BB%A7")
        startActivity(openURL)
    }

    fun Context.shareApp() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("text/plain")
        intent.putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.app_name))
        intent.putExtra(
            Intent.EXTRA_TEXT,
            "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"
        )
        startActivity(Intent.createChooser(intent, resources.getString(R.string.choose_one)))
    }
}