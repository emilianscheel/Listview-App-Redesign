package de.emilianscheel.listview.Objekte;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;


enum DefaultsPreferences {
    LISTEN,
    SETTINGS,
    USER
}

public class Defaults {

    public String PREFERENCE = "";
    public Context context;

    public Defaults(Context context, DefaultsPreferences preferences) {
        switch (preferences) {
            case USER:
                this.PREFERENCE = "NUTZER";
            case SETTINGS:
                this.PREFERENCE = "SETTINGS";
            case LISTEN:
                this.PREFERENCE = "LISTEN";
        }
    }

    public Defaults(Context context) {
        this.context = context;
        this.PREFERENCE = "LISTEN";
    }

    public String getString(String key) {
        return context.getSharedPreferences(PREFERENCE, MODE_PRIVATE).getString(key, null);
    }

    public void setString(String key, String value) {
        context.getSharedPreferences(PREFERENCE, MODE_PRIVATE).edit().putString(key, value).apply();
    }

    public int getInt(String key) {
        return context.getSharedPreferences(PREFERENCE, MODE_PRIVATE).getInt(key, -1);
    }

    public void setInt(String key, int value) {
        context.getSharedPreferences(PREFERENCE, MODE_PRIVATE).edit().putInt(key, value).apply();
    }

    public Boolean getBool(String key) {
        return context.getSharedPreferences(PREFERENCE, MODE_PRIVATE).getBoolean(key, false);
    }

    public Boolean getBool(String key, Boolean defaultValue) {
        return context.getSharedPreferences(PREFERENCE, MODE_PRIVATE).getBoolean(key, defaultValue);
    }

    public void setBool(String key, Boolean value) {
        context.getSharedPreferences(PREFERENCE, MODE_PRIVATE).edit().putBoolean(key, value).apply();
    }

    public <T> ArrayList<T> getArray(String key, Class<T[]> clazz) {
        Gson gson = new Gson();
        String json = new Defaults(context).getString(key);
        if (json == null) {
            return new ArrayList<>();
        }
        return new ArrayList<T>(Arrays.asList(gson.fromJson(json, clazz)));
    }

    public void setArray(String key, ArrayList<?> value) {
        new Defaults(context).setString(key, new Gson().toJson(value));
    }

    public Object getObject(String key, Class<?> clazz) {
        return new Gson().fromJson(new Defaults(context).getString(key), clazz);
    }

    public void setObject(String key, Object object) {
       new Defaults(context).setString(key, new Gson().toJson(object));
    }
}
