package soy.gabimoreno.sharekittens.core.framework

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Environment
import java.io.File

class DownloadGif {

    companion object {
        private const val FILE_NAME = "kitten.gif"
    }

    private lateinit var downloadManager: DownloadManager
    private var enqueue = 0L

    operator fun invoke(
        context: Context,
        url: String,
        onImageDownloaded: (Uri) -> Unit
    ) {
        deleteLastFileIfExists()
        val receiver: BroadcastReceiver = object : BroadcastReceiver() {
            @SuppressLint("Range")
            override fun onReceive(
                context: Context,
                intent: Intent
            ) {
                val action = intent.action
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE == action) {
                    val query = DownloadManager.Query()
                    query.setFilterById(enqueue)
                    val cursor = downloadManager.query(query)
                    if (cursor.moveToFirst()) {
                        val columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                        if (DownloadManager.STATUS_SUCCESSFUL == cursor.getInt(columnIndex)) {
                            val uriString = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI))
                            context.unregisterReceiver(this)
                            onImageDownloaded(Uri.parse(uriString))
                        }
                    }
                }
            }
        }
        context.registerReceiver(
            receiver,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
        downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val request = DownloadManager.Request(Uri.parse(url))
        request.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_PICTURES,
            FILE_NAME
        )
        enqueue = downloadManager.enqueue(request)
    }

    private fun deleteLastFileIfExists() {
        val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val file = File("$dir/$FILE_NAME")
        file.delete()
    }
}
