package de.emilianscheel.listview.NeueListe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.gson.Gson;
import de.emilianscheel.listview.Controller.Activity;
import de.emilianscheel.listview.Controller.OnSwipePopoverListener;
import de.emilianscheel.listview.Controller.SwipePopoverMeta;
import de.emilianscheel.listview.Einstellungen.SettingsField;
import de.emilianscheel.listview.Objekte.Defaults;
import de.emilianscheel.listview.Objekte.ListType;
import de.emilianscheel.listview.R;

public class NewTypeActivity extends AppCompatActivity {

    EditText nameEditText;
    LinearLayout addButton;
    carbon.widget.LinearLayout closeButton;

    ConstraintLayout contentView;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (new Defaults(this).getBool(SettingsField.IS_NIGHT_MODE.getRawValue())) setTheme(R.style.AppTheme_Dark);
        else setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_type);

        nameEditText = findViewById(R.id.NewTypeAct_nameEditText);
        addButton = findViewById(R.id.NewTypeAct_addButton);
        contentView = findViewById(R.id.NewTypeAct_contentView);
        closeButton = findViewById(R.id.NewTypeAct_closeButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Füge die neue Art hinzu.
                handleResult();
                // Schließe die Aktivität.
                finish();
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Schließe die Aktivität.
                finish(true);
            }
        });

        contentView.setOnTouchListener(new OnSwipePopoverListener() {

            @Override
            public SwipePopoverMeta isScrollingUp(View view, SwipePopoverMeta newMeta) {
                // Schließe die Tastatur.
                Activity.hideKeyboardFrom(NewTypeActivity.this, nameEditText);

                super.isScrollingUp(view, newMeta);
                return newMeta;
            }

            @Override
            public SwipePopoverMeta isScrollingDown(View view, SwipePopoverMeta newMeta) {
                // Schließe die Tastatur.
                Activity.hideKeyboardFrom(NewTypeActivity.this, nameEditText);

                super.isScrollingDown(view, newMeta);
                return newMeta;
            }

            @Override
            public SwipePopoverMeta overscrollTop(View view, SwipePopoverMeta newMeta) {
                // Schließe die Tastatur.
                Activity.hideKeyboardFrom(NewTypeActivity.this, nameEditText);

                super.overscrollTop(view, newMeta);
                return newMeta;
            }

            @Override
            public SwipePopoverMeta hasClosed(View view, SwipePopoverMeta newMeta) {
                // Setze den Resultcode auf 0 → keine Daten.
                setResult(0);
                // Schließe die Aktivität.
                finish();
                overridePendingTransition(0, 0);

                return newMeta;
            }
        });
    }

    public void finish(Boolean animated) {
        super.finish();
        if (!animated) {
            overridePendingTransition(0, 0);
        }
    }

    private void handleResult() {
        // Überprüfe, ob der Name der neuen Listenart gegeben ist.
        //Log.i("LISTVIEW", String.valueOf(nameEditText.getText().toString().trim().isEmpty()));
        if (nameEditText.getText().toString().trim().isEmpty()) {
            return;
        }
        // Erstelle das Listenart-Objekt.
        ListType type = new ListType(nameEditText.getText().toString());
        // Gebe die Daten zurück.
        setResult(1, new Intent().putExtra("type", new Gson().toJson(type)));
    }
}