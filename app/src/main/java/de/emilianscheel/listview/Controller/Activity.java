package de.emilianscheel.listview.Controller;

import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.appcompat.app.AppCompatActivity;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Activity {

    AppCompatActivity activity;

    public Activity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void open(Class<?> newActivity) {
        Intent intent = new Intent(activity.getApplicationContext(), newActivity);
        activity.startActivity(intent);
    }

    public void openForResult(Class<?> newActivity) {
        Intent intent = new Intent(activity, newActivity);
        activity.startActivityForResult(intent, 2);
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showKeyboardFrom(Context context, View view) {
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public int getResources(int resId) {
        TypedValue value = new TypedValue();
        activity.getTheme().resolveAttribute(resId, value, true);
        return value.data;
    }
}
