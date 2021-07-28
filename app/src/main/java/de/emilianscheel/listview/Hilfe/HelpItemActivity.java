package de.emilianscheel.listview.Hilfe;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import de.emilianscheel.listview.Controller.Activity;
import de.emilianscheel.listview.Einstellungen.SettingsField;
import de.emilianscheel.listview.Objekte.Defaults;
import de.emilianscheel.listview.R;

public class HelpItemActivity extends AppCompatActivity {

    LinearLayout closeButton;
    TextView title, description;

    HelpItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(new Defaults(this).getBool(SettingsField.IS_NIGHT_MODE.getRawValue()) ? R.style.AppTheme_Dark : R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_item);

        closeButton = findViewById(R.id.HelpItemActivity_closeButton);
        title = findViewById(R.id.HelpItemActivity_title);
        description = findViewById(R.id.HelpItemActivity_description);

        item = (HelpItem) new Defaults(this).getObject("helpItem", HelpItem.class);

        title.setText(item.getTitle());
        description.setText(item.getDescription());

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Öffne die neue Aktivität.
                new Activity(HelpItemActivity.this).open(HelpActivity.class);
            }
        });
    }
}