package de.emilianscheel.listview.Einstellungen;


import android.annotation.SuppressLint;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.emilianscheel.listview.Controller.Activity;
import de.emilianscheel.listview.Mediathek.DashboardActivity;
import de.emilianscheel.listview.Einführung.WelcomeActivity;
import de.emilianscheel.listview.Objekte.Defaults;
import de.emilianscheel.listview.R;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    ArrayList<SettingsItem> items = new ArrayList<>();
    RecyclerView recyclerView;
    SettingsAdapter adapter;
    ImageButton backButton, searchButton;
    TextView titleTextView;
    EditText searchField;

    Boolean isSearch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(new Defaults(this).getBool(SettingsField.IS_NIGHT_MODE.getRawValue()) ? R.style.AppTheme_Dark : R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        recyclerView = findViewById(R.id.SettingsActivity_recyclerView);
        backButton = findViewById(R.id.SettingsActivity_backButton);
        searchButton = findViewById(R.id.SettingsActivity_searchButton);
        titleTextView = findViewById(R.id.TagsActivity_title);
        searchField = findViewById(R.id.SettingsActivity_searchField);

        items = allItems();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Öffne die neue Aktivität.
                new Activity(SettingsActivity.this).open(DashboardActivity.class);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                // Setze auf „Suchen“.
                isSearch = !isSearch;
                titleTextView.setVisibility(isSearch ? View.GONE : View.VISIBLE);
                searchField.setVisibility(isSearch ? View.VISIBLE : View.GONE);
                searchButton.setImageDrawable(isSearch ? getDrawable(R.drawable.icon_xmark) : getDrawable(R.drawable.icon_search));

                if (!isSearch) {
                    Activity.hideKeyboardFrom(SettingsActivity.this, searchField);
                } else {
                    Activity.showKeyboardFrom(SettingsActivity.this, searchField);
                }
            }
        });

        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<SettingsItem> allItems = allItems();
                ArrayList<SettingsItem> items = new ArrayList<>();

                for (SettingsItem item : allItems) {
                    if (item.getTitle().contains(s.toString())) {
                        items.add(item);
                    }
                }

                adapter.setData(items);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        buildRecyclerView();
    }

    private void buildRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SettingsAdapter(this, items);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(true);
    }

    private ArrayList<SettingsItem> allItems() {
        ArrayList<SettingsItem> items = new ArrayList<>();
        /*
        items.add(new SettingsItem("Akzentfarbe", SettingsItemType.SUBTITLE, new SettingsItemViewHolder() {
            @Override
            public void onBindViewHolder(SettingsAdapter.ExampleViewHolder holder, int position) {

            }
        })); */

        items.add(new SettingsItem("Dunkelmodus", SettingsItemType.SWITCH, new SettingsItemViewHolder() {
            @Override
            public void onBindViewHolder(SettingsAdapter.ExampleViewHolder holder, int position) {

                holder.switchButton.setChecked(new Defaults(SettingsActivity.this).getBool(SettingsField.IS_NIGHT_MODE.getRawValue()));

                holder.switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        // Speicher die Einstellung.
                        new Defaults(SettingsActivity.this).setBool(SettingsField.IS_NIGHT_MODE.getRawValue(), isChecked);
                        // Lade die Aktivität neu.
                        new Activity(SettingsActivity.this).open(SettingsActivity.class);
                        overridePendingTransition(R.anim.fade_in_anim, R.anim.fade_out_anim);
                    }
                });
            }
        }));

        items.add(new SettingsItem("Abhaken von Einträgen", SettingsItemType.SWITCH, new SettingsItemViewHolder() {
            @Override
            public void onBindViewHolder(SettingsAdapter.ExampleViewHolder holder, int position) {

                holder.switchButton.setChecked(new Defaults(SettingsActivity.this).getBool(SettingsField.SHOWS_CHECK_BOXES.getRawValue()));

                holder.switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        // Speicher die Einstellung
                        new Defaults(SettingsActivity.this).setBool(SettingsField.SHOWS_CHECK_BOXES.getRawValue(), isChecked);
                    }
                });
            }
        }));

        items.add(new SettingsItem("Einführung starten", SettingsItemType.BUTTON, new SettingsItemViewHolder() {
            @Override
            public void onBindViewHolder(SettingsAdapter.ExampleViewHolder holder, int position) {
                holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Öffne die neue Aktivität.
                        new Activity(SettingsActivity.this).open(WelcomeActivity.class);
                    }
                });
            }
        }));

        items.add(new SettingsItem("Daten und Speicher", SettingsItemType.TITLE));

        items.add(new SettingsItem("Alle Daten löschen", SettingsItemType.BUTTON, new SettingsItemViewHolder() {
            @Override
            public void onBindViewHolder(SettingsAdapter.ExampleViewHolder holder, int position) {


                holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Lösche die Daten der App.
                        deleteData();
                    }
                });
            }
        }));

        items.add(new SettingsItem("Die App zurücksetzen", SettingsItemType.BUTTON, new SettingsItemViewHolder() {
            @Override
            public void onBindViewHolder(SettingsAdapter.ExampleViewHolder holder, int position) {

                holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Lösche die Daten der App.
                        deleteData();
                        // Setze die App zurück.
                        new Defaults(SettingsActivity.this).setBool("reset", true);
                        // Öffne die neue Aktivität.
                        new Activity(SettingsActivity.this).open(WelcomeActivity.class);
                    }
                });
            }
        }));

        return items;
    }

    private void deleteData() {

    }
}
