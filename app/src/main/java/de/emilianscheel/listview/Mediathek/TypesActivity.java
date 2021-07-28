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
import de.emilianscheel.listview.Objekte.ListType;
import de.emilianscheel.listview.Objekte.Model;
import de.emilianscheel.listview.R;

import java.util.ArrayList;

public class TypesActivity extends AppCompatActivity {

    ImageButton backButton, searchButton;
    RecyclerView recyclerView;
    TypesAdapter adapter;
    EditText searchField;
    TextView title;

    ArrayList<ListType> types;
    Boolean isSearch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(new Defaults(this).getBool(SettingsField.IS_NIGHT_MODE.getRawValue()) ? R.style.AppTheme_Dark : R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_types);

        recyclerView = findViewById(R.id.TypesActivity_recyclerView);
        backButton = findViewById(R.id.SettingsActivity_backButton);
        searchField = findViewById(R.id.TypesActivity_searchField);
        searchButton = findViewById(R.id.TypesActivity_searchButton);
        title = findViewById(R.id.TagsActivity_title);

        types = new Model(this).loadAllTypes();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Öffne die neue Aktivität.
                new Activity(TypesActivity.this).open(DashboardActivity.class);
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
                    Activity.hideKeyboardFrom(TypesActivity.this, searchField);
                } else {
                    Activity.showKeyboardFrom(TypesActivity.this, searchField);
                }
            }
        });

        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<ListType> allItems = new Model(TypesActivity.this).loadAllTypes();
                types = new ArrayList<>();

                for (ListType item : allItems) {
                    if (item.getName().contains(s.toString())) {
                        types.add(item);
                    }
                }

                if (types.size() == 0) {
                    types = allItems;
                }

                adapter.setData(types);
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
        adapter = new TypesAdapter(this, types);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(true);
    }
}