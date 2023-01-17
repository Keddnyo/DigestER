package io.github.keddnyo.digester.activities;

import static io.github.keddnyo.digester.repositories.Constants.DATE_PERIOD_END;
import static io.github.keddnyo.digester.repositories.Constants.DATE_PERIOD_START;
import static io.github.keddnyo.digester.repositories.Constants.FORUM_SUBTITLE;
import static io.github.keddnyo.digester.repositories.Constants.FORUM_TITLE;
import static io.github.keddnyo.digester.repositories.Constants.RESPONSE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import io.github.keddnyo.digester.R;

public class ResponseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);

        TextView digestResponseTextView = findViewById(R.id.digestResponse);
        Button digestResponseCopyButton = findViewById(R.id.digestResponseCopyButton);

        Intent intent = getIntent();

        String digestResponse = intent.getStringExtra(RESPONSE);

        String forumTitle = intent.getStringExtra(FORUM_TITLE);
        String forumSubtitle = intent.getStringExtra(FORUM_SUBTITLE);

        String datePeriodStart = intent.getStringExtra(DATE_PERIOD_START);
        String datePeriodEnd = intent.getStringExtra(DATE_PERIOD_END);

        Objects.requireNonNull(getSupportActionBar()).setTitle(datePeriodStart + " - " + datePeriodEnd);
        Objects.requireNonNull(getSupportActionBar()).setSubtitle(forumTitle + " - " + forumSubtitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        digestResponseTextView.setText(digestResponse);

        digestResponseCopyButton.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText(RESPONSE, digestResponse);
            clipboard.setPrimaryClip(clip);

            String textCopied = getString(R.string.response_copied_to_clipboard);
            Toast.makeText(this, textCopied, Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}