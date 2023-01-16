package io.github.keddnyo.digester.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtils {
    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
    public boolean isDateInvalid(String date) {
        try {
            format.parse(date);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }
}
