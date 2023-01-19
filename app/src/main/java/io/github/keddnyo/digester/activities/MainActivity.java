package io.github.keddnyo.digester.activities;

import static io.github.keddnyo.digester.repositories.Constants.FOUR_PDA_PROFILE_URL;
import static io.github.keddnyo.digester.repositories.Constants.GITHUB_PROFILE_URL;
import static io.github.keddnyo.digester.repositories.Constants.GITHUB_REPOSITORY_URL;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import io.github.keddnyo.digester.R;
import io.github.keddnyo.digester.adapters.RecyclerViewAdapter;
import io.github.keddnyo.digester.repositories.ForumSections;
import io.github.keddnyo.digester.requests.UpdateRequest;
import io.github.keddnyo.digester.utils.Application;
import io.github.keddnyo.digester.utils.AsyncTask;
import io.github.keddnyo.digester.utils.DownloadUtil;
import io.github.keddnyo.digester.utils.URLOpener;

public class MainActivity extends AppCompatActivity implements AsyncTask {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.select_forum_section));

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this);

        RecyclerView rv = findViewById(R.id.recyclerView);
        rv.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.addForums(new ForumSections().getForumArrayList());

        new UpdateRequest(this).checkForUpdates();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        try {
            String appName = getString(R.string.app_name);
            String appVersion = new Application().getVersionName(this);

            if (item.getItemId() == R.id.mainMenuAbout) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle(appName + " " + appVersion);
                alertDialogBuilder.setMessage(R.string.about_message);
                alertDialogBuilder.setPositiveButton(R.string.github, (dialog, which) -> new URLOpener().openUrl(this, GITHUB_PROFILE_URL));
                alertDialogBuilder.setNegativeButton(R.string.four_pda, (dialog, which) -> new URLOpener().openUrl(this, FOUR_PDA_PROFILE_URL));
                alertDialogBuilder.setNeutralButton(R.string.repository, (dialog, which) -> new URLOpener().openUrl(this, GITHUB_REPOSITORY_URL));
                alertDialogBuilder.setCancelable(true);
                alertDialogBuilder.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public void showUpdateDialog(String updateUrl, String name, String updateVersion) {
        View rootView = this.getWindow().getDecorView().findViewById(android.R.id.content);
        String label = getString(R.string.update_is_available, updateVersion);
        Snackbar updateIsAvailableSnackbar = Snackbar.make(rootView, label, Snackbar.LENGTH_INDEFINITE);
        updateIsAvailableSnackbar.setAction(R.string.update_download, v -> {
            new DownloadUtil(this).downloadFile(updateUrl, name);
            Snackbar updateIsDownloadingSnackbar = Snackbar.make(rootView, R.string.update_is_downloading, Snackbar.LENGTH_LONG);
            updateIsDownloadingSnackbar.show();
        });
        updateIsAvailableSnackbar.show();
    }
}