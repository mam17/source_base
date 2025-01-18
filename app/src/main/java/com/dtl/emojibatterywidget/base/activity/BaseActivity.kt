package com.dtl.emojibatterywidget.base.activity

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.dtl.emojibatterywidget.R
import com.dtl.emojibatterywidget.domain.layer.LanguageModel
import com.dtl.emojibatterywidget.utils.LocaleHelper
import com.dtl.emojibatterywidget.utils.SpManager
import java.util.Locale


abstract class BaseActivity<V : ViewBinding> : AppCompatActivity() {
    lateinit var viewBinding: V

    open fun onBack() {
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //language setting
        setupLanguage()

        //status bar setting
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true
        hideSystemUI()

        //viewBinding setting
        viewBinding = provideViewBinding()
        setContentView(viewBinding.root)

        // Call initialization methods
        initViews()
        initData()
        initObserver()

        //Handle Back Button Action
        handleOnBackPressed()
    }

    private fun setupLanguage() {
        val language: String = SpManager.getInstance(this).getLanguage().languageCode
        if (language.isNotEmpty()) {
            val locale = Locale(language.lowercase(Locale.getDefault()))
            Locale.setDefault(locale)
            val config = resources.configuration
            config.setLocale(locale)
            createConfigurationContext(config)
        }
    }

    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val controller = WindowCompat.getInsetsController(window, window.decorView)
        controller.let {
            it.hide(WindowInsetsCompat.Type.systemBars())
            it.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }


    open fun initAdmobInterId(): String = ""
    open fun admobConfigLoadInter() = true

    open var isReloadInter = true

    open fun handleOnBackPressed() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBack()
            }
        })
    }

    fun replaceFragment(id: Int, fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            addToBackStack(fragment::class.java.simpleName)
            replace(id, fragment, fragment::class.java.simpleName)
        }
    }

    fun addFragment(id: Int, fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            addToBackStack(fragment::class.java.simpleName)
            add(id, fragment, fragment::class.java.simpleName)
        }
    }

    abstract fun provideViewBinding(): V

    open fun initViews() {}
    open fun initData() {}
    open fun initObserver() {}

    fun showLoading() {

    }

    fun showToast(mes: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, mes, duration).show()
    }

    fun hideLoading() {

    }

    fun setFullscreen() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true
    }

    fun setColorStatusBar(color: Int) {
        setStatusBarColor(ContextCompat.getColor(this, color), true)
    }

    private fun setStatusBarColor(color: Int, lightStatus: Boolean) {
        window.statusBarColor = color
        WindowCompat.getInsetsController(window, window.decorView)?.let { controller ->
            controller.isAppearanceLightStatusBars = lightStatus
        }
    }

    fun visibleView(view: View, boolean: Boolean) {
        if (boolean) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }
}