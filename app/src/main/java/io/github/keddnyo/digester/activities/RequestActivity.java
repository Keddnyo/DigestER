package io.github.keddnyo.digester.activities;

import static io.github.keddnyo.digester.repositories.Constants.FORUM_ID;
import static io.github.keddnyo.digester.repositories.Constants.FORUM_SUBTITLE;
import static io.github.keddnyo.digester.repositories.Constants.FORUM_TITLE;
import static io.github.keddnyo.digester.repositories.Constants.HAS_APPS;
import static io.github.keddnyo.digester.repositories.Constants.RECURSIVE;
import static io.github.keddnyo.digester.repositories.Constants.RESPONSE;
import static io.github.keddnyo.digester.repositories.Constants.DIGEST_TOPIC_LINK;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.Objects;

import io.github.keddnyo.digester.R;
import io.github.keddnyo.digester.requests.DigestRequest;
import io.github.keddnyo.digester.utils.AsyncTask;
import io.github.keddnyo.digester.utils.DateValidator;

public class RequestActivity extends AppCompatActivity implements AsyncTask {
    ImageView requestCloseDialog;
    TextView requestForumTitle, requestForumSubtitle;
    EditText requestPeriodStart, requestPeriodEnd;
    Button requestSubmitButton;
    CircularProgressIndicator requestProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        int forumId = intent.getIntExtra(FORUM_ID, 212);
        String forumTitle = getString(intent.getIntExtra(FORUM_TITLE, R.string.android));
        String forumSubtitle = getString(intent.getIntExtra(FORUM_SUBTITLE, R.string.apps));
        int recursive = intent.getIntExtra(RECURSIVE, 0);
        boolean hasApps = intent.getBooleanExtra(HAS_APPS, false);
        String topicLink = intent.getStringExtra(DIGEST_TOPIC_LINK);

        requestCloseDialog = findViewById(R.id.requestCloseDialog);

        requestForumTitle = findViewById(R.id.requestForumTitle);
        requestForumSubtitle = findViewById(R.id.requestForumSubtitle);

        requestPeriodStart = findViewById(R.id.requestPeriodStart);
        requestPeriodEnd = findViewById(R.id.requestPeriodEnd);

        requestSubmitButton = findViewById(R.id.requestSubmitButton);
        requestProgressBar = findViewById(R.id.requestProgressBar);

        requestForumTitle.setText(forumTitle);
        requestForumSubtitle.setText(forumSubtitle);

        requestCloseDialog.setOnClickListener(v -> finish());

        requestSubmitButton.setOnClickListener(v -> {
            String periodStart, periodEnd;

            periodStart = Objects.requireNonNull(requestPeriodStart.getText()).toString();
            periodEnd = Objects.requireNonNull(requestPeriodEnd.getText()).toString();

            String dateIncorrect = getString(R.string.date_incorrect);

            DateValidator dateValidator = new DateValidator();

            if (dateValidator.isDateInvalid(periodStart) && dateValidator.isDateInvalid(periodEnd)) {
                requestPeriodStart.setError(dateIncorrect);
                requestPeriodEnd.setError(dateIncorrect);
            } else if (dateValidator.isDateInvalid(periodStart)) {
                requestPeriodEnd.setError(dateIncorrect);
            } else if (dateValidator.isDateInvalid(periodEnd)) {
                requestPeriodEnd.setError(dateIncorrect);
            } else {
                executorService.execute(() -> {
                    handler.post(() -> setItemsEnabled(false));

                    String response = new DigestRequest(forumId, recursive, hasApps, periodStart, periodEnd).getDigest();

                    if (response == null) {
                        showErrorToast();
                    } else {
                        Intent responseActivityIntent = new Intent(this, ResponseActivity.class);
                        responseActivityIntent.putExtra(RESPONSE, response);
                        responseActivityIntent.putExtra(DIGEST_TOPIC_LINK, topicLink);
                        responseActivityIntent.putExtra(RECURSIVE, recursive);
                        try {
                            this.startActivity(responseActivityIntent);
                        } catch (Exception e) {
                            showErrorToast();
                        }
                    }

                    handler.post(() -> setItemsEnabled(true));
                });
            }
        });
    }

    private void showErrorToast() {
        String errorHasOccurred = getString(R.string.error_has_occurred);
        handler.post(() -> Toast.makeText(this, errorHasOccurred, Toast.LENGTH_SHORT).show());
    }

    private void setItemsEnabled(boolean bool) {
        int visibility;

        if (bool) {
            visibility = View.GONE;
        } else {
            visibility = View.VISIBLE;
        }

        requestProgressBar.setVisibility(visibility);

        requestPeriodStart.setEnabled(bool);
        requestPeriodEnd.setEnabled(bool);
        requestSubmitButton.setEnabled(bool);
    }
}