package com.arcadio.triplover.utils;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {
    public static final String DATE_FORMAT = "dd-MM-yyyy";

    public static String getDate(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public static long stringToMilliseconds(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);

            Date mDate = sdf.parse(date);
            long timeInMilliseconds = mDate.getTime();

            return timeInMilliseconds;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return System.currentTimeMillis();
    }

    public static String getDateString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy");
        return dateFormat.format(date);
    }

    public static String getDateString(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static String getJsonFromAssets(Context context, String fileName) {
        String jsonString;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return jsonString;
    }

    public String inputStreamToString(InputStream inputStream) {
        try {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, bytes.length);
            String json = new String(bytes);
            return json;
        } catch (IOException e) {
            return null;
        }
    }

    public static int getColorFromResource(Context context, int resourceId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getResources().getColor(resourceId, context.getTheme());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getResources().getColor(resourceId);
        }
        return Color.WHITE;
    }

    public static String timeSeperator(String totalTime) {
        String time = totalTime.split(" ")[1];
        String[] timehhmm = time.split(":");
        return timehhmm[0] + ":" + timehhmm[1];

    }

    public static Gson getGson() {

        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().disableHtmlEscaping().create();
    }
}
