package soy.gabimoreno.sharekittens.core.framework

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Environment

class DownloadGif {

    private lateinit var downloadManager: DownloadManager
    private var enqueue: Long = 0

    operator fun invoke(
        context: Context,
        url: String,
        onImageDownloaded: (Uri) -> Unit
    ) {
        val receiver: BroadcastReceiver = object : BroadcastReceiver() {
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
                            return onImageDownloaded(Uri.parse(uriString))
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
            "foo.gif"
        )
        enqueue = downloadManager.enqueue(request)
    }
}
