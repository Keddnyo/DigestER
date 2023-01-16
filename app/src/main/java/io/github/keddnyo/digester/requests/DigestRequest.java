package io.github.keddnyo.digester.requests;

import static io.github.keddnyo.digester.repositories.Constants.FORUM_SUBTITLE;
import static io.github.keddnyo.digester.repositories.Constants.FORUM_TITLE;
import static io.github.keddnyo.digester.repositories.Constants.RESPONSE;
import static io.github.keddnyo.digester.repositories.Constants.RESPONSE_TIMEOUT;

import android.content.Context;
import android.content.Intent;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;

import io.github.keddnyo.digester.activities.ResponseActivity;
import io.github.keddnyo.digester.utils.AsyncTask;

public class DigestRequest implements AsyncTask {
    Context context;
    int forumId, recursive;
    boolean hasApps;
    String forumTitle, forumSubtitle, periodStart, periodEnd;

    public DigestRequest(Context context, int forumId, String forumTitle, String forumSubtitle, int recursive, boolean hasApps, String periodStart, String periodEnd) {
        this.context = context;
        this.forumId = forumId;
        this.forumTitle = forumTitle;
        this.forumSubtitle = forumSubtitle;
        this.hasApps = hasApps;
        this.recursive = recursive;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
    }

    public void getDigest() {
        AsyncTask.executorService.execute(() -> {
            try {
                String digestType;

                if (hasApps) {
                    digestType = "dig_an_prog.php?";
                } else {
                    digestType = "digest2.php?";
                }

                String urlBuilder = "https://" + "4pda.to" + "/forum/" + digestType + "act=nocache&f=" + forumId + "&" + "date_from=" + periodStart + "&" + "date_to=" + periodEnd + "&" + "recursive=" + recursive;

                URL url = new URL(urlBuilder);

                URLConnection connection = url.openConnection();

                connection.setConnectTimeout(RESPONSE_TIMEOUT);
                connection.setReadTimeout(RESPONSE_TIMEOUT);

                InputStream inputStream = connection.getInputStream();

                int buffedSize = 1024;
                char[] buffer = new char[buffedSize];
                StringBuilder out = new StringBuilder();
                Reader in = new InputStreamReader(inputStream, "CP1251");
                for (int numRead; (numRead = in.read(buffer, 0, buffer.length)) > 0; ) {
                    out.append(buffer, 0, numRead);
                }

                String response = out.toString();

                Intent intent = new Intent(context, ResponseActivity.class);
                intent.putExtra(FORUM_TITLE, forumTitle);
                intent.putExtra(FORUM_SUBTITLE, forumSubtitle);
                intent.putExtra(RESPONSE, response);
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}