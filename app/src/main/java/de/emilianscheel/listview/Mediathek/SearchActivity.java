package de.emilianscheel.listview.Mediathek;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import carbon.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import de.emilianscheel.listview.Controller.Activity;
import de.emilianscheel.listview.Controller.OnSwipePopoverListener;
import de.emilianscheel.listview.Controller.SwipePopoverMeta;
import de.emilianscheel.listview.Einstellungen.SettingsField;
import de.emilianscheel.listview.Objekte.*;
import de.emilianscheel.listview.R;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    LinearLayout closeButton;
    NestedScrollView contentView;
    EditText editText;
    RecyclerView recyclerView;
    SearchAdapter adapter;

    ArrayList<SearchItem> items = new ArrayList<>();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(new Defaults(this).getBool(SettingsField.IS_NIGHT_MODE.getRawValue()) ? R.style.AppTheme_Dark : R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editText = findViewById(R.id.SearchAct_editText);
        contentView = findViewById(R.id.SearchAct_contentView);
        closeButton = findViewById(R.id.SearchAct_closeButton);
        recyclerView = findViewById(R.id.SearchAct_recyclerView);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Schließe die Aktivität.
                finish();
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                items = new ArrayList<>();

                ArrayList<DashboardListItem> lists = new Model(SearchActivity.this).loadAllLists();
                for (DashboardListItem list : lists) {
                    if (list.getName().contains(s.toString())) {
                        items.add(new SearchItem(list.getName(), "Liste"));
                    }
                }

                ArrayList<ListTag> tags = new Model(SearchActivity.this).loadAllTags();
                for (ListTag tag : tags) {
                    if (tag.getName().contains(s.toString())) {
                        items.add(new SearchItem(tag.getName(), "Tag"));
                    }
                }

                ArrayList<ListType> types = new Model(SearchActivity.this).loadAllTypes();
                for (ListType type : types) {
                    if (type.getName().contains(s.toString())) {
                        items.add(new SearchItem(type.getName(), "Art"));
                    }
                }

                for (DashboardListItem list : lists) {
                    for (ListItem item : list.getItems()) {
                        if (item.stringValueFor("title").contains(s.toString())) {
                            items.add(new SearchItem(item.stringValueFor("title"), "Eintrag"));
                        }
                    }
                }

                adapter.setData(items);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        contentView.setOnTouchListener(new OnSwipePopoverListener() {

            @Override
            public SwipePopoverMeta isScrollingUp(View view, SwipePopoverMeta newMeta) {
                // Schließe die Tastatur.
                Activity.hideKeyboardFrom(SearchActivity.this, editText);

                super.isScrollingUp(view, newMeta);
                return newMeta;
            }

            @Override
            public SwipePopoverMeta isScrollingDown(View view, SwipePopoverMeta newMeta) {
                // Schließe die Tastatur.
                Activity.hideKeyboardFrom(SearchActivity.this, editText);

                super.isScrollingDown(view, newMeta);
                return newMeta;
            }

            @Override
            public SwipePopoverMeta overscrollTop(View view, SwipePopoverMeta newMeta) {
                // Schließe die Tastatur.
                Activity.hideKeyboardFrom(SearchActivity.this, editText);

                super.overscrollTop(view, newMeta);
                return newMeta;
            }

            @Override
            public SwipePopoverMeta hasClosed(View view, SwipePopoverMeta newMeta) {
                // Schließe die Aktivität.
                finish();
                overridePendingTransition(0, 0);

                return newMeta;
            }
        });

        buildRecyclerView();
    }

    private void buildRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchAdapter(this, items);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(true);
    }
}