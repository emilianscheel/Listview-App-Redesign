package de.emilianscheel.listview.Mediathek;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import de.emilianscheel.listview.Objekte.ListType;
import de.emilianscheel.listview.R;

import java.util.ArrayList;


public class TypesAdapter extends RecyclerView.Adapter<TypesAdapter.ExampleViewHolder> {

    ArrayList<ListType> types;
    AppCompatActivity activity;

    public void setData(ArrayList<ListType> types) {
        this.types = types;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {

        public TextView title;

        public ExampleViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.itemTags_title);
        }
    }

    public TypesAdapter(AppCompatActivity activity, ArrayList<ListType> types) {
        this.activity = activity;
        this.types = types;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tags, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public int getItemCount() {
        return types.size();
    }

    @Override
    public void onBindViewHolder(final ExampleViewHolder holder, final int position) {

        ListType type = types.get(holder.getAbsoluteAdapterPosition());

        holder.title.setText(type.getName());
    }
}
