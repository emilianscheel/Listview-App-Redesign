package de.emilianscheel.listview.Mediathek;

import android.annotation.SuppressLint;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import androidx.core.widget.NestedScrollView;

import java.util.ArrayList;

import de.emilianscheel.listview.Controller.Activity;
import de.emilianscheel.listview.Controller.OnSwipePopoverListener;
import de.emilianscheel.listview.Controller.SwipePopoverMeta;
import de.emilianscheel.listview.Einstellungen.SettingsField;
import de.emilianscheel.listview.Objekte.DashboardListItem;
import de.emilianscheel.listview.Objekte.Defaults;
import de.emilianscheel.listview.Objekte.Model;
import de.emilianscheel.listview.R;

public class EditListActivity extends AppCompatActivity {

    EditText nameEditText;
    LinearLayout saveButton, deleteButton;
    carbon.widget.LinearLayout closeButton;
    NestedScrollView contentView;

    DashboardListItem list;
    ArrayList<DashboardListItem> lists;

    String identifier;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(new Defaults(this).getBool(SettingsField.IS_NIGHT_MODE.getRawValue()) ? R.style.AppTheme_Dark : R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list);

        nameEditText = findViewById(R.id.EditListAct_nameEditText);
        saveButton = findViewById(R.id.EditListAct_saveButton);
        deleteButton = findViewById(R.id.EditListAct_deleteButton);
        closeButton = findViewById(R.id.EditListAct_closeButton);
        contentView = findViewById(R.id.EditListAct_contentView);


        identifier = new Defaults(this).getString("identifier");
        list = new Model(this).loadList(identifier);
        list = new Model(this).loadList(identifier);
        lists = new Model(this).loadAllLists();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ändere die Liste.
                applyNameChanges();
                // Speicher die Liste.
                new Model(EditListActivity.this).saveList(list);
                // Öffne die neue Aktivität.
                new Activity(EditListActivity.this).open(DashboardActivity.class);
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Öffne die neue Aktivität.
                finish(true);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lösche die Liste.
                deleteList();
                // Speicher die Listen.
                new Model(EditListActivity.this).saveAllLists(lists);
                // Öffne die neue Aktivität.
                new Activity(EditListActivity.this).open(DashboardActivity.class);
            }
        });

        contentView.setOnTouchListener(new OnSwipePopoverListener() {

            @Override
            public SwipePopoverMeta isScrollingUp(View view, SwipePopoverMeta newMeta) {
                // Schließe die Tastatur.
                Activity.hideKeyboardFrom(EditListActivity.this, nameEditText);

                super.isScrollingUp(view, newMeta);
                return newMeta;
            }

            @Override
            public SwipePopoverMeta isScrollingDown(View view, SwipePopoverMeta newMeta) {
                // Schließe die Tastatur.
                Activity.hideKeyboardFrom(EditListActivity.this, nameEditText);

                super.isScrollingDown(view, newMeta);
                return newMeta;
            }

            @Override
            public SwipePopoverMeta overscrollTop(View view, SwipePopoverMeta newMeta) {
                // Schließe die Tastatur.
                Activity.hideKeyboardFrom(EditListActivity.this, nameEditText);

                super.overscrollTop(view, newMeta);
                return newMeta;
            }

            @Override
            public SwipePopoverMeta hasClosed(View view, SwipePopoverMeta newMeta) {
                // Setze den Resultcode auf 0 → keine Daten.
                setResult(0);
                // Schließe die Aktivität.
                finish(false);

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

    public void applyNameChanges() {

        if (nameEditText.getText().toString().trim().isEmpty()) {
            return;
        }
        // Ändere den Namen von der Liste.
        list.setName(nameEditText.getText().toString());
    }

    public void deleteList() {
        for (DashboardListItem listitem : this.lists) {
            if (listitem.getIdentifier().equals(list.getIdentifier())) {
                lists.remove(listitem);
                return;
            }
        }
    }
}