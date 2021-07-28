package de.emilianscheel.listview.NeueListe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.UUID;

import com.google.gson.Gson;
import de.emilianscheel.listview.Controller.Activity;
import de.emilianscheel.listview.Controller.OnSwipePopoverListener;
import de.emilianscheel.listview.Controller.SwipePopoverMeta;
import de.emilianscheel.listview.Mediathek.DashboardActivity;
import de.emilianscheel.listview.Einstellungen.SettingsField;
import de.emilianscheel.listview.Objekte.*;
import de.emilianscheel.listview.R;

public class NewListActivity extends AppCompatActivity {

    EditText nameEditText, tagsEditText;
    RecyclerView recyclerView;
    LinearLayout addButton, contentView;
    NestedScrollView scrollView;
    carbon.widget.LinearLayout closeButton;

    ArrayList<DashboardListItem> lists;
    NewListTypeAdapter adapter;

    ListType type = new ListType();
    ArrayList<ListType> types = new ArrayList<>();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(new Defaults(this).getBool(SettingsField.IS_NIGHT_MODE.getRawValue()) ? R.style.AppTheme_Dark : R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);

        contentView = findViewById(R.id.NewListAct_contentView);
        nameEditText = findViewById(R.id.SearchAct_editText);
        tagsEditText = findViewById(R.id.NewListAct_tagsEditText);
        recyclerView = findViewById(R.id.NewListAct_recyclerView);
        addButton = findViewById(R.id.NewListAct_addButton);
        scrollView = findViewById(R.id.NewListAct_scrollview);
        closeButton = findViewById(R.id.NewListAct_closeButton);

        lists = new Model(this).loadAllLists();
        types = new Model(this).loadAllTypes();
        buildRecyclerView();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Füge die neue Liste hinzu.
                if (!addList()) {
                    return;
                }
                // Speicher die Listen.
                new Model(NewListActivity.this).saveAllLists(lists);
                // Öffne die neue Aktivität.
                new Activity(NewListActivity.this).open(DashboardActivity.class);
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Schließe die Aktivität.
                finish(true);
            }
        });

        scrollView.setOnTouchListener(new OnSwipePopoverListener() {

            @Override
            public SwipePopoverMeta isScrollingUp(View view, SwipePopoverMeta newMeta) {
                // Schließe die Tastatur.
                Activity.hideKeyboardFrom(NewListActivity.this, nameEditText);
                Activity.hideKeyboardFrom(NewListActivity.this, tagsEditText);

                super.isScrollingUp(view, newMeta);
                return newMeta;
            }

            @Override
            public SwipePopoverMeta isScrollingDown(View view, SwipePopoverMeta newMeta) {
                // Schließe die Tastatur.
                Activity.hideKeyboardFrom(NewListActivity.this, nameEditText);
                Activity.hideKeyboardFrom(NewListActivity.this, tagsEditText);

                super.isScrollingDown(view, newMeta);
                return newMeta;
            }

            @Override
            public SwipePopoverMeta overscrollTop(View view, SwipePopoverMeta newMeta) {
                // Schließe die Tastatur.
                Activity.hideKeyboardFrom(NewListActivity.this, nameEditText);
                Activity.hideKeyboardFrom(NewListActivity.this, tagsEditText);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Überprüfe, ob etwas mitgegeben wurde.
        if (resultCode != 1) {
            return;
        }
        // Setze die Listenart auf das mitgegebene.
        type = new Gson().fromJson(data.getStringExtra("type"), ListType.class);
        // Setze den Typ auf den mitgegebenen ListType.
        types.add(type);
        adapter.notifyItemInserted(types.size());
        adapter.setSelectedTypeByIdentifier(type.identifier);
    }

    private void buildRecyclerView() {
        adapter = new NewListTypeAdapter(this, types);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
    }

    private Boolean addList() {

        String[] attributes = nameEditText.getText().toString().split(" ");
        String[] tags = tagsEditText.getText().toString().split(" ");

        if (attributes.length == 0) {
            return false;
        }

        if (tags.length == 0) {
            return false;
        }

        ArrayList<ListType> types = new Model(this).loadAllTypes();

        if (adapter.getSelectedTypeIdentifier() != null && types.size() != 0) type = adapter.getSelectedType();
        else if (type == null) type = new ListType("Einkaufsliste", UUID.randomUUID().toString());

        ArrayList<ListTag> allTags = new Model(this).loadAllTags();
        ArrayList<ListTag> tagsArray = new ArrayList<>();

        for (String tagName : tags) {
            // Falls der Tag in einer anderen Liste schon existiert, füge ihn mit diesem identifier hinzu.
            if (ListTag.verify(allTags, tagName) != null) {
                tagsArray.add(ListTag.verify(allTags, tagName));
            } else {
                tagsArray.add(new ListTag(tagName, UUID.randomUUID().toString()));
            }
        }

        lists.add(new DashboardListItem(attributes[0], type, tagsArray));
        return true;
    }
}
