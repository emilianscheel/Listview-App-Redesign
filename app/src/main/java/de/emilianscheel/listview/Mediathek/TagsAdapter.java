package de.emilianscheel.listview.Mediathek;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import de.emilianscheel.listview.Objekte.ListTag;
import de.emilianscheel.listview.R;

import java.util.ArrayList;


public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.ExampleViewHolder> {

    ArrayList<ListTag> tags;
    AppCompatActivity activity;

    public void setData(ArrayList<ListTag> tags) {
        this.tags = tags;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {

        public TextView title;

        public ExampleViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.itemTags_title);
        }
    }

    public TagsAdapter(AppCompatActivity activity, ArrayList<ListTag> tags) {
        this.activity = activity;
        this.tags = tags;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tags, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    @Override
    public void onBindViewHolder(final ExampleViewHolder holder, final int position) {

        ListTag tag = tags.get(holder.getAbsoluteAdapterPosition());

        holder.title.setText("#" + tag.getName());
    }
}
