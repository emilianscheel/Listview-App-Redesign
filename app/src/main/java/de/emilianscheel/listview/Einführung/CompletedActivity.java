package de.emilianscheel.listview.Einführung;

import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import de.emilianscheel.listview.Controller.Activity;
import de.emilianscheel.listview.Mediathek.DashboardActivity;
import de.emilianscheel.listview.Einstellungen.SettingsField;
import de.emilianscheel.listview.Objekte.Defaults;
import de.emilianscheel.listview.R;

public class CompletedActivity extends AppCompatActivity {

    LinearLayout button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(new Defaults(this).getBool(SettingsField.IS_NIGHT_MODE.getRawValue()) ? R.style.AppTheme_Dark : R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_completed);

        button = findViewById(R.id.IntroCompletedActivity_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Setze das Zurücksetzen.
                new Defaults(CompletedActivity.this).setBool("reset", false);
                // Öffne die neue Aktivität.
                new Activity(CompletedActivity.this).open(DashboardActivity.class);
            }
        });
    }
}