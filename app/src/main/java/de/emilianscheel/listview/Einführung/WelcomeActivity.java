package de.emilianscheel.listview.Einführung;


import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import de.emilianscheel.listview.Controller.Activity;
import de.emilianscheel.listview.Objekte.Helper;
import de.emilianscheel.listview.R;

public class WelcomeActivity extends AppCompatActivity {

    TextView versionTextView;
    LinearLayout nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        versionTextView = findViewById(R.id.WelcomeActivity_versionTextView);
        nextButton = findViewById(R.id.WelcomeActivity_nextButton);

        versionTextView.setText("Version " + Helper.appVersion(this));


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Öffne die neue Aktivität.
                new Activity(WelcomeActivity.this).open(DarkModeActivity.class);
            }
        });
    }
}
