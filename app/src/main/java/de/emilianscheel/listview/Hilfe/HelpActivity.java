package de.emilianscheel.listview.Hilfe;

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
import de.emilianscheel.listview.Mediathek.DashboardActivity;
import de.emilianscheel.listview.Objekte.Defaults;
import de.emilianscheel.listview.R;

import java.util.ArrayList;

public class HelpActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    HelpAdapter adapter;
    ImageButton backButton, searchButton;
    EditText searchField;
    TextView title;

    Boolean isSearch = false;

    ArrayList<HelpItem> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(new Defaults(this).getBool(SettingsField.IS_NIGHT_MODE.getRawValue()) ? R.style.AppTheme_Dark : R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        recyclerView = findViewById(R.id.HelpActivity_recyclerView);
        backButton = findViewById(R.id.SettingsActivity_backButton);
        searchButton = findViewById(R.id.HelpActivity_searchButton);
        searchField = findViewById(R.id.SettingsActivity_searchField);
        title = findViewById(R.id.TagsActivity_title);

        items = allItems();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Öffne neue Aktivität.
                new Activity(HelpActivity.this).open(DashboardActivity.class);
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
                    Activity.hideKeyboardFrom(HelpActivity.this, searchField);
                } else {
                    Activity.showKeyboardFrom(HelpActivity.this, searchField);
                }
            }
        });

        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<HelpItem> allItems = allItems();
                items = new ArrayList<>();

                for (HelpItem item : allItems) {
                    if (item.getTitle().contains(s.toString())) {
                        items.add(item);
                    }
                }

                if (items.size() == 0) {
                    items = allItems;
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
        adapter = new HelpAdapter(this, items);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(true);
    }

    private ArrayList<HelpItem> allItems() {
        ArrayList<HelpItem> items = new ArrayList<>();
        items.add(new HelpItem("Erstellen einer Liste", "Um eine neue Liste zu erstellen, begeben Sie sich in Ihre „Mediathek“.  Der letzte Eintrag zeigt ein „+“. Klicken Sie auf dieses. Die Daten wie der „Name“ der Liste können jetzt eingetragen werden. Bestätigen Sie nun mit Klicken auf „Hinzufügen“."));
        items.add(new HelpItem( "Löschen einer Liste", "Um eine Liste zu löschen, gehen Sie in Ihre „Mediathek“. Die gewünschte Liste kurz gedrückt halten. Nun auf „Löschen“ klicken."));
        items.add(new HelpItem("Neue Listenart erstellen", "Um eine neue Listenart zu erstellen, gehen Sie in Ihre „Mediathek“ und wählen den letzten Eintrag, der ein „+“ abbildet. Weiter auf das „+“ klicken. Im Textfeld den Namen der Listenart eingeben und mit „Hinzufügen“ bestätigen. Listenarten existieren in Kombination mit einer Liste. Sie verschwinden, wenn keine Liste der Listenart zugeordnet ist."));
        items.add(new HelpItem("Abhaken von Einträgen aktivieren bzw. deaktivieren", "Gehen Sie in Ihre „Mediathek“. Über das Icon oben links gehen Sie in die „Einstellungen“. Aktivieren bzw. deaktivieren Sie nun durch Tippen vom Eintrag „Abhaken von Einträgen“."));
        items.add(new HelpItem("Name einer Liste ändern", "Um den Namen einer Liste zu ändern, gehen Sie in Ihre „Mediathek“ und halten Sie die gewünschte Liste kurz gedrückt. Nun den neuen Namen in das obere Textfeld eintragen und mit „Speichern“ bestätigen."));
        items.add(new HelpItem("App zurücksetzen", "Gehen Sie in Ihre „Mediathek“. Über das Icon oben links gehen Sie in die „Einstellungen“. Drücken Sie nun den rot-markierten Eintrag „Die App zurücksetzen“."));
        items.add(new HelpItem("Vorschläge für Einträge einrichten", "Um Einträge vorgeschlagen zu bekommen, erstellen Sie zwei neue Listen mit der gleichen Listenart. Einträge der einen werden jeweils in der anderen vorgeschlagen. So können Sie in die eine wichtige, oft benutzte Einträge speichern, um sie in der anderen aufzurufen."));
        return items;
    }
}