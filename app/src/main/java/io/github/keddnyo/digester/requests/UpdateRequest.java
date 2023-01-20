package io.github.keddnyo.digester.requests;

import static io.github.keddnyo.digester.repositories.Constants.GITHUB_REPOSITORY_LATEST_VERSION_URL;
import static io.github.keddnyo.digester.utils.AsyncTask.executorService;

import org.json.JSONObject;

import io.github.keddnyo.digester.activities.MainActivity;
import io.github.keddnyo.digester.utils.Application;
import io.github.keddnyo.digester.utils.URLReader;
import io.github.keddnyo.digester.utils.Version;

public class UpdateRequest {
    final MainActivity mainActivity;

    public UpdateRequest(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void checkForUpdates() {
        executorService.execute(() -> {
            try {
                String response = new URLReader().readText(GITHUB_REPOSITORY_LATEST_VERSION_URL, null);

                JSONObject jsonObject = new JSONObject(response);

                if (jsonObject.has("tag_name") && !jsonObject.getJSONArray("assets").toString().equals("[]")) {
                    String updateUrl = jsonObject.getJSONArray("assets").getJSONObject(0).getString("browser_download_url");
                    String updateName = jsonObject.getJSONArray("assets").getJSONObject(0).getString("name");
                    String updateVersion = jsonObject.getString("tag_name");
                    String currentVersion = Application.getVersionName(mainActivity);

                    Version update = new Version(updateVersion);
                    Version current = new Version(currentVersion);

                    int compare = update.compareTo(current);

                    if (compare > 0) {
                        mainActivity.runOnUiThread(() -> mainActivity.showUpdateDialog(updateUrl, updateName, updateVersion));
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
