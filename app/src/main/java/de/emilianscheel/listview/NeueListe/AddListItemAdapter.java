package de.emilianscheel.listview.NeueListe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import de.emilianscheel.listview.Controller.Activity;
import de.emilianscheel.listview.Mediathek.MainActivity;
import de.emilianscheel.listview.Objekte.ListItem;
import de.emilianscheel.listview.Objekte.ListItemAttribute;
import de.emilianscheel.listview.R;

import java.util.ArrayList;

public class AddListItemAdapter extends RecyclerView.Adapter<AddListItemAdapter.ExampleViewHolder> {

    MainActivity activity;
    ArrayList<ListItem> items;
    RecyclerView recyclerView;
    Context context;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public ImageButton addButton;
        public ImageView addButton2;
        public EditText editText;
        public LinearLayout linearLayout;
        public ConstraintLayout constraintLayout;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.itemListAdd_titleTextView);
            addButton = itemView.findViewById(R.id.itemListAdd_addButton);
            editText = itemView.findViewById(R.id.itemListAdd_editText);
            linearLayout = itemView.findViewById(R.id.itemListAdd_linearLayout);
            constraintLayout = itemView.findViewById(R.id.itemListAdd_constrainLayout);
            addButton2 = itemView.findViewById(R.id.itemListAdd_addButton2);
        }
    }

    public AddListItemAdapter(MainActivity activity, RecyclerView recyclerView, ArrayList<ListItem> items) {
        this.context = activity.getApplicationContext();
        this.activity = activity;
        this.items = items;
        this.recyclerView = recyclerView;
    }

    @Override
    public int getItemCount() {
        return items.size() + 1;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_add, parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ExampleViewHolder holder, final int position) {

        if (holder.getAbsoluteAdapterPosition() == 0) {

            holder.linearLayout.setVisibility(View.GONE);
            holder.constraintLayout.setVisibility(View.VISIBLE);

            holder.addButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Schließe die Tastatur.
                    Activity.hideKeyboardFrom(context, holder.editText);

                    if (holder.editText.getText().toString().trim().isEmpty()) {
                        return;
                    }
                    // Füge den Eintrag zur Liste hinzu.
                    activity.insertItem(createNewItem(holder.editText.getText().toString()));
                    // Speicher die Liste.
                    activity.saveList();
                }
            });

        } else {

            holder.linearLayout.setVisibility(View.VISIBLE);
            holder.constraintLayout.setVisibility(View.GONE);

            final ListItem item = items.get(holder.getAbsoluteAdapterPosition()-1);

            holder.titleTextView.setText(item.stringValueFor("title"));

            holder.addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Füge den Eintrag zur Liste hinzu.
                    activity.insertItem(item);
                    // Speicher die Liste.
                    activity.saveList();
                }
            });
        }
    }

    public ListItem createNewItem(String text) {

        String[] attributes = text.split(" ");

        ListItem item = new ListItem();
        item.addAttribute(new ListItemAttribute("title", attributes[0]));

        if (attributes.length > 1) {
            item.addAttribute(new ListItemAttribute("description", attributes[1]));
        }
        if (attributes.length > 2) {
            item.addAttribute(new ListItemAttribute("quantity", attributes[2]));
        }
        if (attributes.length > 3) {
            item.addAttribute(new ListItemAttribute("unit", attributes[3]));
        }
        return item;
    }
}
