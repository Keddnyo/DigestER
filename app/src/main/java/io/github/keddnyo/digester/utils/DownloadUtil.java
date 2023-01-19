package io.github.keddnyo.digester.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

public class DownloadUtil {
    final Context context;

    public DownloadUtil(Context context) {
        this.context = context;
    }

    public void downloadFile(String url, String name) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        String downloadDir = Environment.DIRECTORY_DOWNLOADS;
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(name);
        request.setDestinationInExternalPublicDir(downloadDir, name);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setAllowedOverRoaming(false);
        downloadManager.enqueue(request);
    }
}
