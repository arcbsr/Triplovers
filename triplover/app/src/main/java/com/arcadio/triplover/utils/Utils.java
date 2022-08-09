package com.arcadio.triplover.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {
//    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static String getDate(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public static long stringToMilliseconds(String date, String format) {
        if (date == null || date.isEmpty()) {
            return System.currentTimeMillis();
        }
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
        DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_SEARCH);
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
        try {
            String time = totalTime.split(" ")[1];
            String[] timehhmm = time.split(":");
            return timehhmm[0] + ":" + timehhmm[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalTime;

    }

    public static void openTermAndCondition(Context context) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.TERM_CONDITION));
        context.startActivity(browserIntent);
    }

    public static void openExternalLink(Context context, String url) {
        if (!url.contains("http://") && !url.contains("https://")) {
            url = "http://" + url;
        }
        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(browserIntent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Link is not valid", Toast.LENGTH_SHORT).show();
        }
    }

    public static Gson getGson() {

        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().disableHtmlEscaping().create();
    }

    public static String convertNumberOrS(int number, String text, String ext) {
        return number + " " + text + ((number < 2) ? "" : ext);

    }
    public static String getDeviceID(Context context){
        String deviceId = "Unknown";
        try {
            deviceId = android.provider.Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        KLog.w("myid>>"+deviceId);
        return deviceId;
    }
}
