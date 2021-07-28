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

public class IntroTagsActivity extends AppCompatActivity {

    Switch switchButtonTags, switchButtonTypes;
    carbon.widget.LinearLayout tagsButton, typeButton;
    LinearLayout nextButton;

    int tagsHeight, typeHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(new Defaults(this).getBool(SettingsField.IS_NIGHT_MODE.getRawValue()) ? R.style.AppTheme_Dark : R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_tags);

        switchButtonTags = findViewById(R.id.IntroTagsActivity_switchButtonTags);
        switchButtonTypes = findViewById(R.id.IntroTagsActivity_switchButtonTypes);
        tagsButton = findViewById(R.id.IntroTagsActivity_tagButton);
        typeButton = findViewById(R.id.IntroTagsActivity_typeButton);
        nextButton = findViewById(R.id.IntroTagsActivity_nextButton);

        switchButtonTypes.setChecked(new Defaults(this).getBool(SettingsField.DASHBOARD_TYPES_SHOWN.getRawValue()));
        switchButtonTags.setChecked(new Defaults(this).getBool(SettingsField.DASHBOARD_TAGS_SHOWN.getRawValue()));

        switchButtonTypes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Speicher die Einstellungen.
                new Defaults(IntroTagsActivity.this).setBool(SettingsField.DASHBOARD_TYPES_SHOWN.getRawValue(), isChecked);
            }
        });

        switchButtonTags.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Speicher die Einstellungen.
                new Defaults(IntroTagsActivity.this).setBool(SettingsField.DASHBOARD_TAGS_SHOWN.getRawValue(), isChecked);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Öffne die neue Aktivität.
                new Activity(IntroTagsActivity.this).open(IntroHelpActivity.class);
            }
        });
    }
}