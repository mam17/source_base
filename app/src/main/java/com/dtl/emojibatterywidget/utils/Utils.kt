package com.dtl.emojibatterywidget.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.dtl.emojibatterywidget.R
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utils {

    private fun setShimmerDrawable(
        context: Context
    ): ShimmerDrawable {
        val shimmer = Shimmer.ColorHighlightBuilder()
            .setDuration(1000)
            .setBaseAlpha(0.9f)
            .setHighlightAlpha(0.93f)
            .setWidthRatio(1.5f)
            .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
            .setAutoStart(true)
            .setBaseColor(context.getColor(R.color.gray))
            .setHighlightColor(context.getColor(R.color.white))
            .build()
        return ShimmerDrawable().apply {
            setShimmer(shimmer)
        }

    }

    fun setImageLoad(
        context: Context,
        urlString: String,
        ivItemWallpaper: ImageView
    ) {
        Glide.with(context)
            .load(urlString)
            .dontAnimate()
            .centerCrop()
            .fitCenter()
            .placeholder(setShimmerDrawable(context))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(ivItemWallpaper)
    }

    fun getImagesFromAssets(context: Context, folder: String): List<String> {
        val assetManager = context.assets
        val imageList = mutableListOf<String>()

        try {
            val files = assetManager.list(folder)
            files?.let {
                for (fileName in it) {
                    imageList.add("$folder/$fileName")
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return imageList
    }

    fun setBackgroundUsingGlide(context: Context, assetPath: String, onResult: ((Bitmap) -> Unit)? = null) {
        val assetUri = "file:///android_asset/$assetPath"

        Glide.with(context)
            .asBitmap()
            .load(assetUri)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    // Set background cho RelativeLayout
                    onResult?.invoke(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    //
                }
            })
    }

    fun setImageToBitmap(context: Context, strPath: String, onResult: ((Bitmap) -> Unit)? = null){
        Glide.with(context)
            .asBitmap()
            .load(strPath)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    // Set background cho RelativeLayout
                    onResult?.invoke(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    //
                }
            })
    }

    fun exportRelative(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(
            view.width,
            view.height,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    fun saveBitmapToCache(context: Context, bitmap: Bitmap): File? {
        try {
            val cacheDir = context.cacheDir
            val iconChangerDir = File(cacheDir, "Transparent")
            if (!iconChangerDir.exists()) {
                iconChangerDir.mkdirs()
            }

            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val fileName = "Transparent_$timestamp.png"
            val file = File(iconChangerDir, fileName)

            FileOutputStream(file).use { outputStream ->
                bitmap.compress(
                    Bitmap.CompressFormat.PNG,
                    100,
                    outputStream
                )
                outputStream.flush()
            }

            return file
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun deleteFileCache(context: Context): Boolean {
        val cacheDir = context.cacheDir
        val iconChangerDir = File(cacheDir, "Transparent")

        if (iconChangerDir.exists() && iconChangerDir.isDirectory) {
            val files = iconChangerDir.listFiles()

            files?.forEach {
                it.delete()
            }
            return true
        }
        return false
    }
}