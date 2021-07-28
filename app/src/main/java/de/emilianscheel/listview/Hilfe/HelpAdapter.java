package de.emilianscheel.listview.Hilfe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import de.emilianscheel.listview.Controller.Activity;
import de.emilianscheel.listview.Objekte.Defaults;
import de.emilianscheel.listview.R;

import java.util.ArrayList;


public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.ExampleViewHolder> {

    ArrayList<HelpItem> items;

    AppCompatActivity activity;
    Context context;

    public void setData(ArrayList<HelpItem> items) {
        this.items = items;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView description;

        public ExampleViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.itemHelp_title);
            description = itemView.findViewById(R.id.itemHelp_description);
        }
    }

    public HelpAdapter(AppCompatActivity activity, ArrayList<HelpItem> items) {
        this.activity = activity;
        this.context = activity.getApplicationContext();
        this.items = items;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_help, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(final ExampleViewHolder holder, final int position) {

        final HelpItem item = items.get(holder.getAbsoluteAdapterPosition());

        holder.title.setText(item.getTitle());
        holder.description.setText(item.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Speicher die Daten.
                new Defaults(activity).setObject("helpItem", item);
                // Öffne die neue Aktivität.
                new Activity(activity).open(HelpItemActivity.class);
            }
        });
    }
}
