package de.emilianscheel.listview.Einführung;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import de.emilianscheel.listview.Controller.Activity;
import de.emilianscheel.listview.Einstellungen.SettingsField;
import de.emilianscheel.listview.Objekte.Defaults;
import de.emilianscheel.listview.R;

public class DarkModeActivity extends AppCompatActivity {

    Switch switchButton;
    LinearLayout nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(new Defaults(this).getBool(SettingsField.IS_NIGHT_MODE.getRawValue()) ? R.style.AppTheme_Dark : R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_dark_mode);

        switchButton = findViewById(R.id.DarkModeActivity_switchButton);
        nextButton = findViewById(R.id.DarkModeActivity_nextButton);

        switchButton.setChecked(new Defaults(this).getBool(SettingsField.IS_NIGHT_MODE.getRawValue()));

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Speicher die Einstellung.
                new Defaults(DarkModeActivity.this).setBool(SettingsField.IS_NIGHT_MODE.getRawValue(), isChecked);
                // Lade die Aktivität neu.
                new Activity(DarkModeActivity.this).open(DarkModeActivity.class);
                overridePendingTransition(R.anim.fade_in_anim, R.anim.fade_out_anim);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Öffne die neue Aktivität.
                new Activity(DarkModeActivity.this).open(CheckBoxActivity.class);
            }
        });
    }
}