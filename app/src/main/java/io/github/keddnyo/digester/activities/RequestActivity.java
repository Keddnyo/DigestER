package io.github.keddnyo.digester.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import io.github.keddnyo.digester.R;
import io.github.keddnyo.digester.utils.DateValidator;

public class RequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
    }

    @Override
    protected void onResume() {
        super.onResume();

        EditText requestPeriodStart, requestPeriodEnd;
        Button requestSubmitButton;

        requestPeriodStart = findViewById(R.id.requestPeriodStart);
        requestPeriodEnd = findViewById(R.id.requestPeriodEnd);
        requestSubmitButton = findViewById(R.id.requestSubmitButton);

        DateValidator dateValidator = new DateValidator();

        requestSubmitButton.setOnClickListener(v -> {
            String periodStart, periodEnd;

            periodStart = requestPeriodStart.getText().toString();
            periodEnd = requestPeriodEnd.getText().toString();

            String dateIsEmpty = getString(R.string.date_is_empty);
            String dateIncorrect = getString(R.string.date_incorrect);

            if (!periodStart.equals("")) {
                requestPeriodStart.setError(dateIsEmpty);
            } else if (!periodEnd.equals("")) {
                requestPeriodEnd.setError(dateIsEmpty);
            } else if (dateValidator.isDateInvalid(periodStart)) {
                requestPeriodStart.setError(dateIncorrect);
            } else if (dateValidator.isDateInvalid(periodEnd)) {
                requestPeriodEnd.setError(dateIncorrect);
            } else {

            }
        });
    }
}