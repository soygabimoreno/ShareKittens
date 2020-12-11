package soy.gabimoreno.libImageloader

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

fun ImageView.load(thumbnailUrl: String) {
    Glide
        .with(context)
        .load(thumbnailUrl)
        .into(this)
}

fun String?.toBitmap(
    context: Context,
    onBitmapReady: (bitmap: Bitmap) -> Unit
) {
    Glide
        .with(context)
        .asBitmap()
        .load(this)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .getResource {
            if (it != null) {
                onBitmapReady(it)
            }
        }
        .submit()
}

fun <TranscodeType> RequestBuilder<TranscodeType>.getResource(
    listener: (resource: TranscodeType?) -> Unit
): RequestBuilder<TranscodeType> {
    return addListener(
        object : RequestListener<TranscodeType> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<TranscodeType>?,
                isFirstResource: Boolean
            ): Boolean {
                listener(null)
                return false
            }

            override fun onResourceReady(
                resource: TranscodeType?,
                model: Any?,
                target: Target<TranscodeType>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                listener(resource)
                return false
            }
        })
}
