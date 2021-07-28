package de.emilianscheel.listview.Mediathek;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.emilianscheel.listview.Controller.Activity;
import de.emilianscheel.listview.Einstellungen.SettingsField;
import de.emilianscheel.listview.Objekte.Defaults;
import de.emilianscheel.listview.Objekte.ListTag;
import de.emilianscheel.listview.Objekte.Model;
import de.emilianscheel.listview.R;

import java.util.ArrayList;

public class TagsActivity extends AppCompatActivity {

    ImageButton backButton;
    RecyclerView recyclerView;
    TagsAdapter adapter;
    EditText searchField;
    ImageButton searchButton;
    TextView title;

    ArrayList<ListTag> tags;
    Boolean isSearch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(new Defaults(this).getBool(SettingsField.IS_NIGHT_MODE.getRawValue()) ? R.style.AppTheme_Dark : R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags);

        recyclerView = findViewById(R.id.TagsActivity_recyclerView);
        backButton = findViewById(R.id.SettingsActivity_backButton);
        searchButton = findViewById(R.id.TagsActivity_searchButton);
        searchField = findViewById(R.id.TagsActivity_searchField);
        title = findViewById(R.id.TagsActivity_title);

        tags = new Model(this).loadAllTags();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Öffne die neue Aktivität.
                new Activity(TagsActivity.this).open(DashboardActivity.class);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                // Setze auf „Suchen“.
                isSearch = !isSearch;
                title.setVisibility(isSearch ? View.GONE : View.VISIBLE);
                searchField.setVisibility(isSearch ? View.VISIBLE : View.GONE);
                searchButton.setImageDrawable(isSearch ? getDrawable(R.drawable.icon_xmark) : getDrawable(R.drawable.icon_search));

                if (!isSearch) {
                    Activity.hideKeyboardFrom(TagsActivity.this, searchField);
                } else {
                    Activity.showKeyboardFrom(TagsActivity.this, searchField);
                }
            }
        });

        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<ListTag> allItems = new Model(TagsActivity.this).loadAllTags();
                tags = new ArrayList<>();

                for (ListTag item : allItems) {
                    if (item.getName().contains(s.toString())) {
                        tags.add(item);
                    }
                }

                if (tags.size() == 0) {
                    tags = allItems;
                }

                adapter.setData(tags);
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
        adapter = new TagsAdapter(this, tags);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(true);
    }
}