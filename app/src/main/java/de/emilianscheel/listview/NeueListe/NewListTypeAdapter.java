package de.emilianscheel.listview.NeueListe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.emilianscheel.listview.Controller.Activity;
import de.emilianscheel.listview.Objekte.ListType;
import de.emilianscheel.listview.R;


public class NewListTypeAdapter extends RecyclerView.Adapter<NewListTypeAdapter.ExampleViewHolder> {

    ArrayList<ListType> types;
    String selectedIdentifier = "";

    AppCompatActivity activity;
    Context context;

    public void setSelectedTypeByIdentifier(String identifier) {
        this.selectedIdentifier = identifier;
    }

    public String getSelectedTypeIdentifier() {
        return this.selectedIdentifier;
    }

    public ListType getSelectedType() {
        for (ListType type : this.types) {
            if (type.identifier.equals(this.selectedIdentifier)) {
                return type;
            }
        }
        return null;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {

        public TextView subtitleText, nameText;
        public LinearLayout linearLayout;
        public ImageView add;
        public ImageView selectedIcon;

        public ExampleViewHolder(View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.itemType_nameTextView);
            subtitleText = itemView.findViewById(R.id.itemType_subtitleTextView);
            linearLayout = itemView.findViewById(R.id.itemType_linearLayout);
            selectedIcon = itemView.findViewById(R.id.itemType_selectedIcon);
            add = itemView.findViewById(R.id.itemType_add);
        }
    }

    public NewListTypeAdapter(NewListActivity activity, ArrayList<ListType> types) {
        this.activity = activity;
        this.context = activity.getApplicationContext();
        this.types = types;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public int getItemCount() {
        return types.size() + 1;
    }

    @Override
    public void onBindViewHolder(final ExampleViewHolder holder, final int position) {

        if (holder.getAbsoluteAdapterPosition() == 0) {

            holder.linearLayout.setVisibility(View.INVISIBLE);
            holder.add.setVisibility(View.VISIBLE);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Öffne die neue Aktivität.
                    new Activity(activity).openForResult(NewTypeActivity.class);
                    // Ergebnisse gehen an -> NewListActivity -> onActivityResult()
                }
            });

        } else  {

            holder.add.setVisibility(View.INVISIBLE);
            holder.linearLayout.setVisibility(View.VISIBLE);

            final ListType type = types.get(holder.getAbsoluteAdapterPosition() - 1);
            holder.nameText.setText(type.name);

            if (getSelectedTypeIdentifier().equals(type.identifier)) {
                holder.selectedIcon.setVisibility(View.VISIBLE);
            } else {
                holder.selectedIcon.setVisibility(View.INVISIBLE);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Wähle den Typ aus.
                    setSelectedTypeByIdentifier(type.identifier);
                    notifyDataSetChanged();
                }
            });
        }
    }
}
