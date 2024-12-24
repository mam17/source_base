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
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.dtl.emojibatterywidget.R
import com.dtl.emojibatterywidget.domain.layer.LanguageModel
import com.dtl.emojibatterywidget.utils.LocaleHelper
import com.dtl.emojibatterywidget.utils.SpManager


abstract class BaseActivity<V : ViewBinding> : AppCompatActivity(), LifecycleOwner {
    lateinit var viewBinding: V
    lateinit var language: LanguageModel
    open fun onBack() {
        finish()
    }

    override fun attachBaseContext(newBase: Context) {
        language = SpManager.getInstance(this).getLanguage()
        val context = LocaleHelper.setLocale(newBase, language.languageCode)
        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        SpManager.getInstance(this).getLanguage().let {
//            val language: String = it.languageCode
//            if (language.isNotEmpty()) {
//                val res = resources
//                val dm = res.displayMetrics
//                val conf = res.configuration
//                conf.setLocale(Locale(language.lowercase(Locale.getDefault())))
//                res.updateConfiguration(conf, dm)
//            }
//        }

        setStatusBarColor(R.color.color_bg)

        hideSystemUI()
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true

        viewBinding = provideViewBinding()
        setContentView(viewBinding.root)
        initViews()
        initData()
        initObserver()
        handleOnBackPressed()
    }

    private fun hideSystemUI() {
        val decorView = window.decorView
        val uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        decorView.systemUiVisibility = uiOptions
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

    fun setFullscreen() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true
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

    fun setStatusBarColor(color: Int) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, color)
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = true
        }
    }

}