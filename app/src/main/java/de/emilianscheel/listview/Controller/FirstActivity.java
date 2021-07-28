package de.emilianscheel.listview.Controller;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import de.emilianscheel.listview.Mediathek.DashboardActivity;
import de.emilianscheel.listview.Einführung.WelcomeActivity;
import de.emilianscheel.listview.Einstellungen.SettingsField;
import de.emilianscheel.listview.Objekte.Defaults;
import de.emilianscheel.listview.R;

public class FirstActivity extends AppCompatActivity {

    Boolean reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(new Defaults(this).getBool(SettingsField.IS_NIGHT_MODE.getRawValue()) ? R.style.AppTheme_Dark : R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        reset = new Defaults(this).getBool("reset", true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (reset) {
            // Öffne die neue Aktivität.
            new Activity(this).open(WelcomeActivity.class);

        } else {
            // Öffne die neue Aktivität.
            new Activity(this).open(DashboardActivity.class);
        }
    }
}
