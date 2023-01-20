package io.github.keddnyo.digester.activities;

import static io.github.keddnyo.digester.repositories.Constants.DIGEST_HAS_NO_MORE_NEW_PROGRAMS;
import static io.github.keddnyo.digester.repositories.Constants.DIGEST_HAS_NO_MORE_UPDATES;
import static io.github.keddnyo.digester.repositories.Constants.RESPONSE;
import static io.github.keddnyo.digester.repositories.Constants.DIGEST_TOPIC_LINK;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import io.github.keddnyo.digester.R;
import io.github.keddnyo.digester.utils.BBCode;
import io.github.keddnyo.digester.utils.Clipboard;

public class ResponseActivity extends AppCompatActivity {

    private boolean showPreview;
    TextView digestResponseTextView;
    String digestTopicLink;
    String digestResponse;
    Spanned digestHtmlResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);

        digestResponseTextView = findViewById(R.id.digestResponse);

        Intent intent = getIntent();
        digestResponse = intent.getStringExtra(RESPONSE);
        digestTopicLink = intent.getStringExtra(DIGEST_TOPIC_LINK);

        String htmlResponse = BBCode.parse(digestResponse);
        digestHtmlResponse = HtmlCompat.fromHtml(htmlResponse, HtmlCompat.FROM_HTML_MODE_COMPACT);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        showPreview = !sharedPreferences.getBoolean("settings_digest_use_preview", true);
        switchPreviewMode(showPreview);
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        if (digestResponse.contains(DIGEST_HAS_NO_MORE_NEW_PROGRAMS) && digestResponse.contains(DIGEST_HAS_NO_MORE_UPDATES)) {
            menu.findItem(R.id.menuResponsePublish).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_response, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.menuResponseCopy) {
            copyResponse();
        } else if (item.getItemId() == R.id.menuResponsePreview) {
            switchPreviewMode(showPreview);
        } else if (item.getItemId() == R.id.menuResponsePublish) {
            copyResponse();
            openDigestTopic();
        } else {
            onBackPressed();
        }

        return true;
    }

    private void copyResponse() {
        new Clipboard().copyText(this, RESPONSE, digestResponse);
        String textCopied = getString(R.string.response_copied_to_clipboard);
        Toast.makeText(this, textCopied, Toast.LENGTH_LONG).show();
    }

    private void openDigestTopic() {
        Intent digestTopicIntent = new Intent(Intent.ACTION_VIEW);
        digestTopicIntent.setData(Uri.parse(digestTopicLink));
        startActivity(digestTopicIntent);
    }

    private void switchPreviewMode(boolean isPreviewShown) {
        if (isPreviewShown) {
            digestResponseTextView.setText(digestResponse);
            Objects.requireNonNull(getSupportActionBar()).setSubtitle(R.string.response_code_preview);
            showPreview = false;
        } else {
            digestResponseTextView.setText(digestHtmlResponse);
            Objects.requireNonNull(getSupportActionBar()).setSubtitle(R.string.response_basic_preview);
            showPreview = true;
        }
    }
}