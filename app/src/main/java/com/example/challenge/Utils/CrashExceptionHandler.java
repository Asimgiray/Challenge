package com.example.challenge.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.challenge.CrashActivity;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;

public class CrashExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static String TAG = CrashExceptionHandler.class.getSimpleName();
    private boolean DEBUG = true;
    private final String LINE_SEPARATOR = "\n";

    private static final StringBuilder errorMessage = new StringBuilder();
    private static final StringBuilder deviceInfo = new StringBuilder();
    private static final StringBuilder dateInfo = new StringBuilder();

    private final Context context;

    public CrashExceptionHandler(Context context) {
        this.context = context;
    }

    @Override
    public void uncaughtException(@NonNull Thread thread, @NonNull Throwable exception) {

        StringWriter stackTrace = new StringWriter();
        exception.printStackTrace(new PrintWriter(stackTrace));

        Log("A crash occured in the app");

        errorMessage.append(stackTrace.toString());
        Log("ErrorMessage: " + errorMessage.toString());

        deviceInfo.append("Brand: ");
        deviceInfo.append(Build.BRAND);
        deviceInfo.append(LINE_SEPARATOR);
        deviceInfo.append("Device: ");
        deviceInfo.append(Build.DEVICE);
        deviceInfo.append(LINE_SEPARATOR);
        deviceInfo.append("Model: ");
        deviceInfo.append(Build.MODEL);
        deviceInfo.append(LINE_SEPARATOR);
        deviceInfo.append("Id: ");
        deviceInfo.append(Build.ID);
        deviceInfo.append(LINE_SEPARATOR);
        deviceInfo.append("Product: ");
        deviceInfo.append(Build.PRODUCT);
        deviceInfo.append(LINE_SEPARATOR);
        Log("DeviceInfo: " + deviceInfo.toString());

        dateInfo.append(Calendar.getInstance().getTime());
        Log("Date: " + dateInfo.toString());

        Intent intent = new Intent(context, CrashActivity.class);
        context.startActivity(intent);

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(2);


    }

    private void Log(String message) {
        if (DEBUG)
            Log.d(TAG, message);
    }
}
