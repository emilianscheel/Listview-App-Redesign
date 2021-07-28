package de.emilianscheel.listview.Mediathek;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.emilianscheel.listview.Controller.Activity;
import de.emilianscheel.listview.Objekte.DashboardListItem;
import de.emilianscheel.listview.Objekte.Defaults;
import de.emilianscheel.listview.NeueListe.NewListActivity;
import de.emilianscheel.listview.R;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ExampleViewHolder> {

    ArrayList<DashboardListItem> lists;
    DashboardActivity activity;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {

        public TextView description, date, title, subtitle;
        public LinearLayout linearLayout;
        public ImageView add;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            subtitle = itemView.findViewById(R.id.itemDashboard_subtitle);
            description = itemView.findViewById(R.id.itemDashboard_description);
            title = itemView.findViewById(R.id.itemDashboard_title);
            date = itemView.findViewById(R.id.itemDashboard_date);
            linearLayout = itemView.findViewById(R.id.itemDashboard_linearLayout);
            add = itemView.findViewById(R.id.itemDashboard_add);
        }
    }

    public ListAdapter(DashboardActivity activity, ArrayList<DashboardListItem> lists) {
        this.activity = activity;
        this.lists = lists;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dashboard, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public int getItemCount() {
        return lists.size() + 1;
    }

    @Override
    public void onBindViewHolder(final ExampleViewHolder holder, final int position) {

        if (holder.getAbsoluteAdapterPosition() == lists.size()) {

            holder.linearLayout.setVisibility(View.INVISIBLE);
            holder.add.setVisibility(View.VISIBLE);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Öffne die neue Aktivität.
                    new Activity(activity).open(NewListActivity.class);
                }
            });

        } else {

            holder.add.setVisibility(View.INVISIBLE);
            holder.linearLayout.setVisibility(View.VISIBLE);

            final DashboardListItem list = lists.get(holder.getAbsoluteAdapterPosition());

            holder.title.setText(list.getName());
            holder.description.setText(list.getType().name);
            holder.subtitle.setText(list.getItems().size() + " Einträge");
            holder.date.setText(list.getDate("dd. MMM yyyy"));

            if (list.getItems().size() == 1) {
                holder.subtitle.setText("1 Eintrag");
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Setze die aktuelle Liste.
                    new Defaults(activity).setString("identifier", list.getIdentifier());
                    // Öffne die neue Aktivität.
                    new Activity(activity).open(MainActivity.class);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // Setze die aktuelle Liste.
                    new Defaults(activity).setString("identifier", list.getIdentifier());
                    // Öffne die neue Aktivität.
                    new Activity(activity).open(EditListActivity.class);
                    return false;
                }
            });
        }
    }
}
