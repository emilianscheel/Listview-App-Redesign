package de.emilianscheel.listview.Einstellungen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import de.emilianscheel.listview.Controller.Activity;
import de.emilianscheel.listview.R;

import java.util.ArrayList;


public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ExampleViewHolder> {

    ArrayList<SettingsItem> items;

    AppCompatActivity activity;
    Context context;

    public void setData(ArrayList<SettingsItem> items) {
        this.items = items;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {

        public TextView title, value, sectionTitle;
        public ImageView button;
        public Switch switchButton;
        public LinearLayout linearLayout;
        public carbon.widget.LinearLayout rootLayout;

        public ExampleViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.itemSettings_title);
            value = itemView.findViewById(R.id.itemSettings_value);
            button = itemView.findViewById(R.id.itemSettings_button);
            switchButton = itemView.findViewById(R.id.itemSettings_switch);
            sectionTitle = itemView.findViewById(R.id.itemSettings_sectionTitle);
            linearLayout = itemView.findViewById(R.id.itemSettings_linearLayout);
            rootLayout = itemView.findViewById(R.id.itemSettings_rootLayout);
        }
    }

    public SettingsAdapter(SettingsActivity activity, ArrayList<SettingsItem> items) {
        this.activity = activity;
        this.context = activity.getApplicationContext();
        this.items = items;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_settings, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(final ExampleViewHolder holder, final int position) {

        final SettingsItem item = items.get(holder.getAbsoluteAdapterPosition());

        if (item.getType() == SettingsItemType.BUTTON) {

            holder.switchButton.setVisibility(View.GONE);
            holder.button.setVisibility(View.VISIBLE);
            holder.value.setVisibility(View.GONE);
            holder.sectionTitle.setVisibility(View.GONE);
            holder.linearLayout.setVisibility(View.VISIBLE);
            holder.rootLayout.setElevationShadowColor(new Activity(activity).getResources(R.attr.shadowColor));
            holder.itemView.setBackgroundColor(new Activity(activity).getResources(R.attr.backgroundSecondary));
            holder.itemView.setElevation(5);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
            params.bottomMargin = 6;
            holder.itemView.setLayoutParams(params);

            holder.title.setText(item.getTitle());

        } else if (item.getType() == SettingsItemType.SWITCH) {

            holder.switchButton.setVisibility(View.VISIBLE);
            holder.button.setVisibility(View.GONE);
            holder.value.setVisibility(View.GONE);
            holder.sectionTitle.setVisibility(View.GONE);
            holder.linearLayout.setVisibility(View.VISIBLE);
            holder.rootLayout.setElevationShadowColor(new Activity(activity).getResources(R.attr.shadowColor));
            holder.itemView.setBackgroundColor(new Activity(activity).getResources(R.attr.backgroundSecondary));
            holder.itemView.setElevation(5);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
            params.bottomMargin = 6;
            holder.itemView.setLayoutParams(params);

            holder.title.setText(item.getTitle());

        } else if (item.getType() == SettingsItemType.SUBTITLE) {

            holder.switchButton.setVisibility(View.GONE);
            holder.button.setVisibility(View.VISIBLE);
            holder.value.setVisibility(View.VISIBLE);
            holder.sectionTitle.setVisibility(View.GONE);
            holder.linearLayout.setVisibility(View.VISIBLE);

            holder.rootLayout.setElevationShadowColor(new Activity(activity).getResources(R.attr.shadowColor));
            holder.itemView.setBackgroundColor(new Activity(activity).getResources(R.attr.backgroundSecondary));
            holder.itemView.setElevation(5);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
            params.bottomMargin = 6;
            holder.itemView.setLayoutParams(params);

            holder.title.setText(item.getTitle());
            holder.value.setText(item.getValue());

        } else if (item.getType() == SettingsItemType.TITLE) {

            holder.linearLayout.setVisibility(View.GONE);
            holder.sectionTitle.setVisibility(View.VISIBLE);

            holder.rootLayout.setElevationShadowColor(null);
            holder.rootLayout.setElevation(0);
            holder.itemView.setBackground(null);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
            params.bottomMargin = 0;
            holder.itemView.setLayoutParams(params);
            holder.itemView.setPadding(0, 0, 0, 0);

            holder.sectionTitle.setText(item.getTitle());
        }
        item.onBindViewHolder(holder, position);
    }
}
