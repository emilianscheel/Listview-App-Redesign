package de.emilianscheel.listview.NeueListe;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;

import de.emilianscheel.listview.Objekte.DashboardListItem;
import de.emilianscheel.listview.Mediathek.MainActivity;
import de.emilianscheel.listview.Einstellungen.SettingsField;
import de.emilianscheel.listview.Objekte.Defaults;
import de.emilianscheel.listview.Objekte.ListItem;
import de.emilianscheel.listview.Objekte.ListItemAttribute;
import de.emilianscheel.listview.Objekte.Model;
import de.emilianscheel.listview.R;

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ExampleViewHolder> {

    DashboardListItem list;
    MainActivity activity;
    RecyclerView recyclerView;
    Context context;
    HashMap<Integer, ExampleViewHolder> viewHolders = new HashMap<>();

    public void setData(DashboardListItem list) {
        this.list = list;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public ImageButton deleteButton;
        public CheckBox checkBox;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.itemList_titleTextView);
            deleteButton = itemView.findViewById(R.id.itemList_deleteButton);
            checkBox = itemView.findViewById(R.id.itemList_checkBox);
        }
    }

    public ListItemAdapter(MainActivity activity, RecyclerView recyclerView, DashboardListItem list) {
        this.context = activity.getApplicationContext();
        this.list = list;
        this.activity = activity;
        this.recyclerView = recyclerView;
    }

    @Override
    public int getItemCount() {
        return list.getItems().size();
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(final ExampleViewHolder holder, final int position) {

        if (!viewHolders.containsKey(position)) viewHolders.put(position, holder);

        ListItem item = list.getItems().get(position);

        holder.titleTextView.setText(item.stringValueFor("title"));

        holder.deleteButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // Lösche den Eintrag.
                removeAt(holder.getAbsoluteAdapterPosition());
                // Speicher die Liste.
                new Model(context).saveList(list);

                return false;
            }
        });

        Boolean showsCheckBoxes = new Defaults(context).getBool(SettingsField.SHOWS_CHECK_BOXES.getRawValue());

        if (showsCheckBoxes) {
            holder.checkBox.setVisibility(View.VISIBLE);
        } else {
            holder.checkBox.setVisibility(View.GONE);
        }

        holder.checkBox.setChecked(item.booleanValueFor("isChecked"));

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!item.setAttribute("isChecked", isChecked)) {
                    item.addAttribute(new ListItemAttribute("isChecked", isChecked));
                }
                // Speicher die Liste.
                new Model(context).saveList(list);
            }
        });
    }

    public ExampleViewHolder getViewByPosition(int position) {
        return viewHolders.get(position);
    }

    public void removeAt(int position) {
        list.removeItem(position);
        notifyItemRemoved(position);
        notifyItemRangeRemoved(position, 1);
        requestLayoutItemRemoved();
    }

    public void requestLayoutItemRemoved() {
        ExampleViewHolder holder = getViewByPosition(0);
        requestLayoutHorizontally(holder, -1);
    }

    public void requestLayoutItemInserted() {
        ExampleViewHolder holder = onCreateViewHolder(this.recyclerView, 0);
        requestLayoutHorizontally(holder, 1);
    }

    private void requestLayout(int valueBefore, int valueOfInsertedItem) {
        ValueAnimator animator = ValueAnimator.ofInt(valueBefore, valueBefore + valueOfInsertedItem);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
                layoutParams.height = (int) animation.getAnimatedValue();;
                ListItemAdapter.this.recyclerView.setLayoutParams(layoutParams);
            }
        });
        animator.setDuration(300);
        animator.start();
    }

    private void requestLayoutHorizontally(ExampleViewHolder holder, int multiplicator) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
        // Lade die Maße vom View neu.
        holder.itemView.measure(0, 0);
        int itemHeight = multiplicator * (holder.itemView.getMeasuredHeight() + params.topMargin + params.bottomMargin);
        requestLayout(this.recyclerView.getHeight(), itemHeight);
    }

    private void requestLayoutVertically(ExampleViewHolder holder, int multiplicator) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
        // Lade die Maße vom View neu.
        holder.itemView.measure(0, 0);
        int itemHeight = multiplicator * (holder.itemView.getMeasuredWidth() + params.leftMargin + params.rightMargin);
        requestLayout(this.recyclerView.getHeight(), itemHeight);
    }
}
