package de.emilianscheel.listview.Mediathek;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.emilianscheel.listview.Controller.Activity;
import de.emilianscheel.listview.Einstellungen.SettingsField;
import de.emilianscheel.listview.NeueListe.AddListItemAdapter;
import de.emilianscheel.listview.NeueListe.ListItemAdapter;
import de.emilianscheel.listview.Objekte.DashboardListItem;
import de.emilianscheel.listview.Objekte.Defaults;
import de.emilianscheel.listview.Objekte.ListItem;
import de.emilianscheel.listview.Objekte.Model;
import de.emilianscheel.listview.R;

public class MainActivity extends AppCompatActivity {

    ImageButton backButton, searchButton;
    TextView titleTextView;
    EditText searchField;

    DashboardListItem list;
    RecyclerView recyclerView;
    ListItemAdapter adapter;

    ArrayList<ListItem> addItems;
    RecyclerView addRecyclerView;
    AddListItemAdapter addAdapter;

    LinearLayout contentView;

    String identifier;
    Boolean isSearch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (new Defaults(this).getBool(SettingsField.IS_NIGHT_MODE.getRawValue())) setTheme(R.style.AppTheme_Dark);
        else setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentView = findViewById(R.id.MainActivity_contentView);
        recyclerView = findViewById(R.id.MainActivity_recyclerView);
        addRecyclerView = findViewById(R.id.MainActivity_addRecyclerView);
        searchButton = findViewById(R.id.MainActivity_searchButton);
        backButton = findViewById(R.id.MainActivity_backButton);
        titleTextView = findViewById(R.id.MainActivity_titleTextView);
        searchField = findViewById(R.id.MainActivity_searchField);

        identifier = new Defaults(this).getString("identifier");
        list = new Model(this).loadList(identifier);
        addItems = new Model(this).loadExampleItems(list);

        buildRecyclerView();
        buildAddRecyclerView();

        titleTextView.setText(list.getName());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Speicher die Liste.
                new Model(MainActivity.this).saveList(list);
                // Öffne die neue Aktivität.
                new Activity(MainActivity.this).open(DashboardActivity.class);
            }
        });

        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DashboardListItem list = new Model(MainActivity.this).loadList(identifier);
                ArrayList<ListItem> items = new ArrayList<>();

                Log.i("LISTVIEW", String.valueOf(list.getItems().size()));

                for (ListItem item : list.getItems()) {
                    if (item.stringValueFor("title").contains(s.toString())) {
                        items.add(item);
                    }
                }

                if (items.size() == 0) {
                    items = list.getItems();
                }

                list.setItems(items);

                adapter.setData(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

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
                    Activity.hideKeyboardFrom(MainActivity.this, searchField);
                } else {
                    Activity.showKeyboardFrom(MainActivity.this, searchField);
                }
            }
        });
    }

    public void insertItem(ListItem item) {
        list.addItem(item);
        adapter.notifyItemInserted(list.getItems().size()-1);
        adapter.notifyItemRangeInserted(list.getItems().size()-1, 1);
        adapter.requestLayoutItemInserted();
    }

    public void saveList() {
        // Speicher die Liste.
        new Model(this).saveList(list);
    }

    private void buildRecyclerView() {
        recyclerView.setHasFixedSize(true);
        adapter = new ListItemAdapter(this, recyclerView, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

    private void buildAddRecyclerView() {
        addRecyclerView.setHasFixedSize(true);
        addAdapter = new AddListItemAdapter(this, addRecyclerView, addItems);
        addRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        addRecyclerView.setAdapter(addAdapter);
        addRecyclerView.setNestedScrollingEnabled(false);
    }

    public void shareList() {
        String message = shareMessage();

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(shareIntent, "Wähle eine Methode zum teilen deiner Einkausliste aus!"));
    }

    private String shareMessage() {
        String message = list.getName();

        for (int i = 0; i < list.getItems().size(); i++) {
            ListItem item = list.getItems().get(i);
            for (int i1 = 0; i1 < item.getAttributes().size(); i1 ++) {
                message += " " + item.getAttributes().get(i1).getStringValue();
            }
        }
        return message;
    }
}