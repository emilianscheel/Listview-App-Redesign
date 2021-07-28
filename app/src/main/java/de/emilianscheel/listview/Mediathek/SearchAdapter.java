package de.emilianscheel.listview.Mediathek;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import de.emilianscheel.listview.Objekte.SearchItem;
import de.emilianscheel.listview.R;

import java.util.ArrayList;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ExampleViewHolder> {

    ArrayList<SearchItem> items;
    AppCompatActivity activity;

    public void setData(ArrayList<SearchItem> items) {
        this.items = items;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {

        public TextView title, description;

        public ExampleViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.itemSearch_title);
            description = itemView.findViewById(R.id.itemSearch_description);
        }
    }

    public SearchAdapter(AppCompatActivity activity, ArrayList<SearchItem> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(final ExampleViewHolder holder, final int position) {

        SearchItem item = items.get(holder.getAbsoluteAdapterPosition());

        holder.title.setText(item.getTitle());
        holder.description.setText(item.getDescription());
    }
}
