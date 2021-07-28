package de.emilianscheel.listview.Mediathek;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.emilianscheel.listview.Controller.Activity;
import de.emilianscheel.listview.Einstellungen.SettingsActivity;
import de.emilianscheel.listview.Einstellungen.SettingsField;
import de.emilianscheel.listview.Hilfe.HelpActivity;
import de.emilianscheel.listview.Objekte.DashboardListItem;
import de.emilianscheel.listview.Objekte.Defaults;
import de.emilianscheel.listview.Objekte.Model;
import de.emilianscheel.listview.R;

public class DashboardActivity extends AppCompatActivity {

    ImageButton settingsButton;
    TextView listTextView, tagsCount, typesCount;
    carbon.widget.LinearLayout searchButton, accountButton;
    LinearLayout tagButton, typeButton;

    ArrayList<DashboardListItem> lists;
    RecyclerView recyclerView;
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(new Defaults(this).getBool(SettingsField.IS_NIGHT_MODE.getRawValue()) ? R.style.AppTheme_Dark : R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        settingsButton = findViewById(R.id.DashboardAct_settingsButton);
        searchButton = findViewById(R.id.SettingsActivity_searchButton);
        accountButton = findViewById(R.id.DashboardAct_accountButton);
        tagButton = findViewById(R.id.DashboardAct_tagButton);
        typeButton = findViewById(R.id.DashboardAct_typeButton);
        recyclerView = findViewById(R.id.DashboardAct_recyclerView);
        listTextView = findViewById(R.id.DashboardAct_listTextView);
        tagsCount = findViewById(R.id.DashboardAct_tagsCount);
        typesCount = findViewById(R.id.DashboardAct_typesCount);

        lists = new Model(this).loadAllLists();
        buildRecyclerView();

        tagButton.setVisibility(new Defaults(this).getBool(SettingsField.DASHBOARD_TAGS_SHOWN.getRawValue()) ? View.VISIBLE : View.GONE);
        typeButton.setVisibility(new Defaults(this).getBool(SettingsField.DASHBOARD_TYPES_SHOWN.getRawValue()) ? View.VISIBLE : View.GONE);

        typesCount.setText(new Model(this).loadAllTypes().size() + " Stück");
        tagsCount.setText(new Model(this).loadAllTags().size() + " Stück");

        listTextView.setVisibility(new Defaults(this).getBool(SettingsField.DASHBOARD_TYPES_SHOWN.getRawValue()) || new Defaults(this).getBool(SettingsField.DASHBOARD_TAGS_SHOWN.getRawValue()) ? View.VISIBLE : View.GONE);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Öffne die neue Aktivität.
                new Activity(DashboardActivity.this).open(SettingsActivity.class);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Öffne die neue Aktivität.
                new Activity(DashboardActivity.this).open(SearchActivity.class);
            }
        });

        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Öffne die neue Aktivität.
                new Activity(DashboardActivity.this).open(HelpActivity.class);
            }
        });

        tagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Öffne die neue Aktivität.
                new Activity(DashboardActivity.this).open(TagsActivity.class);
            }
        });

        typeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Öffne die neue Aktivität.
                new Activity(DashboardActivity.this).open(TypesActivity.class);
            }
        });
    }

    public void buildRecyclerView() {
        adapter = new ListAdapter(this, lists);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
    }

    public void clear() {
        lists.clear();
        adapter.notifyAll();
    }
}