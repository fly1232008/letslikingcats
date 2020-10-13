package com.example.letslikingcats.features.downloadfiles

import android.app.DownloadManager
import android.net.Uri
import android.os.Environment
import io.reactivex.Completable
import javax.inject.Inject

class FileManager @Inject constructor(val downloadManager: DownloadManager) {
    /**
     * упрощенная релазиция без отслеживания окончания процесса
     */
    fun saveByUrl(url: String): Completable {
        val req = DownloadManager.Request(Uri.parse(url))
        req.setTitle("Cat")
            .setDescription("Downloading ....")
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "cat_ ${System.currentTimeMillis()}"
            )
        downloadManager.enqueue(req)
        return Completable.complete()
    }
}